package com.dscreate_app.weatherdata.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VpAdapter(
    fragAct: FragmentActivity,
    private val list: List<Fragment>
): FragmentStateAdapter(fragAct) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}