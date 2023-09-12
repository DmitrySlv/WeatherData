package com.dscreate_app.weatherdata.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscreate_app.weatherdata.adapters.WeatherAdapter
import com.dscreate_app.weatherdata.databinding.FragmentHoursBinding
import com.dscreate_app.weatherdata.models.WeatherModelDto
import com.dscreate_app.weatherdata.view_models.MainViewModel
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private var _binding: FragmentHoursBinding? = null
    private val binding: FragmentHoursBinding
        get() = _binding ?: throw RuntimeException("FragmentHoursBinding is null")

    private lateinit var adapter: WeatherAdapter
    private val viewModel: MainViewModel by activityViewModels()

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
        viewModel.liveDataCurrent.observe(viewLifecycleOwner) {
           adapter.submitList(getHoursList(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRcView() = with(binding) {
        adapter = WeatherAdapter(null)
        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = adapter
    }

    private fun getHoursList(wItem: WeatherModelDto): List<WeatherModelDto> {
        val hoursArray = JSONArray(wItem.hours)
        val tempList = mutableListOf<WeatherModelDto>()
        for (i in 0 until hoursArray.length()) {
            val item = WeatherModelDto(
                wItem.city,
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("text"),
                (hoursArray[i] as JSONObject).getString("temp_c").toFloat().toInt().toString(),
                "",
                "",
                (hoursArray[i] as JSONObject).getJSONObject("condition")
                    .getString("icon"),
                ""
            )
            tempList.add(item)
        }
        return tempList
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}