package com.dscreate_app.weatherdata.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscreate_app.weatherdata.R
import com.dscreate_app.weatherdata.adapters.WeatherAdapter
import com.dscreate_app.weatherdata.databinding.FragmentDaysBinding
import com.dscreate_app.weatherdata.view_models.MainViewModel

class DaysFragment : Fragment() {

    private var _binding: FragmentDaysBinding? = null
    private val binding: FragmentDaysBinding
        get() = _binding ?: throw RuntimeException("FragmentDaysBinding is null")
    private lateinit var weatherAdapter: WeatherAdapter
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        updateData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() = with(binding) {
        rcDays.layoutManager = LinearLayoutManager(requireContext())
        weatherAdapter = WeatherAdapter()
        rcDays.adapter = weatherAdapter
    }

    private fun updateData() {
        mainViewModel.liveDataList.observe(viewLifecycleOwner) {
            weatherAdapter.submitList(it.subList(1, it.size))
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}