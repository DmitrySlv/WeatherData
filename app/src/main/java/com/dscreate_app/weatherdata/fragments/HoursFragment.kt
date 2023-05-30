package com.dscreate_app.weatherdata.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscreate_app.weatherdata.adapters.WeatherAdapter
import com.dscreate_app.weatherdata.databinding.FragmentHoursBinding
import com.dscreate_app.weatherdata.models.WeatherModel
import com.dscreate_app.weatherdata.view_models.MainViewModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private var _binding: FragmentHoursBinding? = null
    private val binding: FragmentHoursBinding
        get() = _binding ?: throw RuntimeException("FragmentHoursBinding is null")
    private lateinit var weatherAdapter: WeatherAdapter

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        updateData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRcView() = with(binding) {
        rcViewHours.layoutManager = LinearLayoutManager(requireContext())
        weatherAdapter = WeatherAdapter(null)
        rcViewHours.adapter = weatherAdapter
    }

    private fun updateData() {
        mainViewModel.liveDataCurrent.observe(viewLifecycleOwner) {
           weatherAdapter.submitList(getHoursList(it))
        }
    }

    private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
        val hoursArray = JSONArray(wItem.hours)
        val list = mutableListOf<WeatherModel>()
        for (i in 0 until hoursArray.length()) {
            val item =  WeatherModel(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "", "",
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }
        return list
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}