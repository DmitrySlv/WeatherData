package com.dscreate_app.weatherdata.fragments

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dscreate_app.weatherdata.R
import com.dscreate_app.weatherdata.adapters.VpAdapter
import com.dscreate_app.weatherdata.databinding.FragmentMainBinding
import com.dscreate_app.weatherdata.utils.isPermissionGranted
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    private lateinit var permLauncher: ActivityResultLauncher<String>
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

    companion object {

        private const val HOURS = "Почасовой прогноз"
        private const val DAYS = "Прогноз на день"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}