package com.dscreate_app.weatherdata.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dscreate_app.weatherdata.adapters.WeatherAdapter
import com.dscreate_app.weatherdata.databinding.FragmentDaysBinding
import com.dscreate_app.weatherdata.models.WeatherModelDto
import com.dscreate_app.weatherdata.view_models.MainViewModel

class DaysFragment : Fragment(), WeatherAdapter.Listener {

    private var _binding: FragmentDaysBinding? = null
    private val binding: FragmentDaysBinding
        get() = _binding ?: throw RuntimeException("FragmentDaysBinding is null")

    private lateinit var adapter: WeatherAdapter
    private val viewModel: MainViewModel by activityViewModels()

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
        viewModel.liveDataList.observe(viewLifecycleOwner) {
            adapter.submitList(it.subList(1, it.size))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun init() = with(binding) {
        adapter = WeatherAdapter(this@DaysFragment)
        rcView.layoutManager = LinearLayoutManager(requireContext())
        rcView.adapter = adapter
    }

    override fun onClick(item: WeatherModelDto) {
        viewModel.liveDataCurrent.value = item
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}