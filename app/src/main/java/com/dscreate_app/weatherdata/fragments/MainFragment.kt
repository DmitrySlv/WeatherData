package com.dscreate_app.weatherdata.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dscreate_app.weatherdata.adapters.VpAdapter
import com.dscreate_app.weatherdata.databinding.FragmentMainBinding
import com.dscreate_app.weatherdata.models.WeatherModelDto
import com.dscreate_app.weatherdata.utils.DialogManager
import com.dscreate_app.weatherdata.utils.isPermissionGranted
import com.dscreate_app.weatherdata.utils.showToast
import com.dscreate_app.weatherdata.view_models.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var pLauncher: ActivityResultLauncher<String>
    private val fragList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tabList = listOf(
        "Hours", "Days"
    )
    private lateinit var fLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        init()
        onClicks()
        updateCurrentCard()
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    private fun init() = with(binding) {
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val vpAdapter = VpAdapter(activity as FragmentActivity, fragList)
        vp.adapter = vpAdapter
        TabLayoutMediator(tabLayout, vp) {
            tab, pos -> tab.text = tabList[pos]
        }.attach()
    }

    private fun onClicks() = with(binding) {
        ibSync.setOnClickListener {
            tabLayout.selectTab(tabLayout.getTabAt(0))
            getLocation()
            checkLocation()
        }

        ibSearch.setOnClickListener {
            DialogManager.searchByNameDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    name?.let { name -> requestWeatherData(name) }
                }
            })
        }
    }

    private fun checkLocation() {
        if (isLocationEnabled()) {
            getLocation()
        } else {
            DialogManager.locationSettingDialog(requireContext(), object : DialogManager.Listener {
                override fun onClick(name: String?) {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
            })
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun getLocation() {
        val cancellationToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token
        ).addOnCompleteListener {
            requestWeatherData("${it.result.latitude}, ${it.result.longitude}")
        }
    }

    private fun updateCurrentCard() = with(binding) {
        viewModel.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMinTemp = "max: ${it.maxTemp} C° / min: ${it.minTemp} C°"
            tvDate.text = it.time
            tvCity.text = it.city
            tvCurrentTemp.text = if (it.currentTemp.isEmpty()) {
                maxMinTemp
            } else {
                it.currentTemp.toFloat().toInt().toString() + " C°"
            }
            tvCondition.text = it.condition
            tvMaxMin.text = if (it.currentTemp.isEmpty()) "" else maxMinTemp
            Picasso.get().load("https:" + it.image).into(imWeather)
        }
    }

    private fun permissionListener() {
        pLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            showToast("Permission is $it")
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeatherData(city: String) {
        val url = BASE_URL + API_KEY + "&q=$city" + "&days=$DAYS_COUNT" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(requireContext())
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseWeatherData(result)
            },

            { error ->
                Log.d(TAG, "Error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObj = JSONObject(result)
        val list = parseDays(mainObj)
        parseCurrentData(mainObj, list[0])
    }

    private fun parseCurrentData(mainObj: JSONObject, weatherItem: WeatherModelDto) {
        val item = WeatherModelDto(
            mainObj.getJSONObject("location").getString("name"),
            mainObj.getJSONObject("current").getString("last_updated"),
            mainObj.getJSONObject("current").getJSONObject("condition")
                .getString("text"),
            mainObj.getJSONObject("current").getString("temp_c"),
            weatherItem.minTemp,
            weatherItem.maxTemp,
            mainObj.getJSONObject("current").getJSONObject("condition")
                .getString("icon"),
            weatherItem.hours
        )
        viewModel.liveDataCurrent.value = item
    }

    private fun parseDays(mainObj: JSONObject): List<WeatherModelDto> {
        val list = mutableListOf<WeatherModelDto>()
        val daysArray = mainObj.getJSONObject("forecast")
            .getJSONArray("forecastday")
        val name = mainObj.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModelDto(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        viewModel.liveDataList.value = list
        return list
    }

    companion object {
        private const val BASE_URL = "https://api.weatherapi.com/v1/forecast.json?key="
        private const val API_KEY = "788394c0eb1c4e5b8e1183129221805"
        private const val DAYS_COUNT = "3"
        const val TAG = "MyLog"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}