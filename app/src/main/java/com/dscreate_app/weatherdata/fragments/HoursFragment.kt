package com.dscreate_app.weatherdata.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscreate_app.weatherdata.adapters.WeatherAdapter
import com.dscreate_app.weatherdata.databinding.FragmentHoursBinding
import com.dscreate_app.weatherdata.models.WeatherModel

class HoursFragment : Fragment() {

    private var _binding: FragmentHoursBinding? = null
    private val binding: FragmentHoursBinding
        get() = _binding ?: throw RuntimeException("FragmentHoursBinding is null")
    private lateinit var weatherAdapter: WeatherAdapter

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRcView() = with(binding) {
        rcViewHours.layoutManager = LinearLayoutManager(requireContext())
        weatherAdapter = WeatherAdapter()
        rcViewHours.adapter = weatherAdapter
        val tempList = listOf(
            WeatherModel(
                "",
                "12:00",
                "Солнечно",
                "+25°С",
                "",
                "",
                "",
                ""
            ),
            WeatherModel(
                "",
                "13:00",
                "Солнечно",
                "+30°С",
                "",
                "",
                "",
                ""
            ),
            WeatherModel(
                "",
                "14:00",
                "Солнечно",
                "+35°С",
                "",
                "",
                "",
                ""
            )
        )
        weatherAdapter.submitList(tempList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}