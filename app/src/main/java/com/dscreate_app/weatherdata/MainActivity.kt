package com.dscreate_app.weatherdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dscreate_app.weatherdata.databinding.ActivityMainBinding
import com.dscreate_app.weatherdata.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commit()
    }
}