package com.dscreate_app.weatherdata.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dscreate_app.weatherdata.adapters.VpAdapter
import com.dscreate_app.weatherdata.databinding.FragmentMainBinding
import com.dscreate_app.weatherdata.models.WeatherModel
import com.dscreate_app.weatherdata.utils.isPermissionGranted
import com.dscreate_app.weatherdata.view_models.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import org.json.JSONObject

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var permLauncher: ActivityResultLauncher<String>
    private val mainViewModel: MainViewModel by activityViewModels()
    private val fList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tabList = listOf(
        HOURS,
        DAYS
    )

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
        requestWeatherData("Tolyatti")
        updateCurrentCard()
    }

    private fun updateCurrentCard() = with(binding) {
        mainViewModel.liveDataCurrent.observe(viewLifecycleOwner) {
            val maxMinTemp = "${it.maxTemp}°C / ${it.minTemp}°C"
            tvDate.text = it.time
            tvCity.text = it.city
            tvCondition.text = it.condition
            tvCurrentTemp.text = it.currentTemp.ifEmpty { maxMinTemp }
            tvMaxMinTemp.text = if (it.currentTemp.isEmpty()) "" else maxMinTemp
            Picasso.get().load(HTTPS_URL_IMAGE + it.imageUrl).into(imWeather)
        }
    }

    private fun init() = with(binding) {
        val vpAdapter = VpAdapter(activity as FragmentActivity, fList)
        viewPager.adapter = vpAdapter
        TabLayoutMediator(tabLayout, viewPager) {
            tab, position -> tab.text = tabList[position]
        }.attach()
    }

    private fun permissionListener() {
        permLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(requireContext(), "Permission is: $it", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            permLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun requestWeatherData(city: String) {
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                result ->
                parseWeatherData(result)
            },
            {
                error ->
                Log.d("MyLog", "error: $error")
            }
        )
        queue.add(request)
    }

    private fun parseWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val list = parseDays(mainObject)
        parseCurrentData(mainObject, list[0])
    }

    private fun parseCurrentData(mainObject: JSONObject, weatherItem: WeatherModel) {
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition")
                .getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            weatherItem.maxTemp,
            weatherItem.minTemp,
            mainObject.getJSONObject("current").getJSONObject("condition")
                .getString("icon"),
            weatherItem.hours
        )
        mainViewModel.updateCurrentData(item)
        Log.d("myLog", item.imageUrl)
    }

    private fun parseDays(mainObject: JSONObject): List<WeatherModel> {
        val list = mutableListOf<WeatherModel>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        val name = mainObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = WeatherModel(
                name,
                day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                "",
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        mainViewModel.updateDataList(list)
        return list
    }

    companion object {

        private const val HOURS = "Почасовой прогноз"
        private const val DAYS = "Прогноз на день"
        private const val HTTPS_URL_IMAGE = "https:"
        private const val API_KEY = "788394c0eb1c4e5b8e1183129221805"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}