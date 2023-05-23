package com.dscreate_app.weatherdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.dscreate_app.weatherdata.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bGet.setOnClickListener {
            getResult(CITY_NAME)
        }
    }

    private fun getResult(name: String) {
        val url = "$BASE_URL?key=$API_KEY&q=$name&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                    response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                    Log.d("MyLog", "Response: ${temp.getString("temp_c")}")
            },
            {
                error ->
                Log.d("MyLog", "Volley error: $error")
            }
        )
        queue.add(stringRequest)
    }

    companion object {
        private const val BASE_URL = "https://api.weatherapi.com/v1/forecast.json"
        private const val API_KEY = "788394c0eb1c4e5b8e1183129221805"
        private const val CITY_NAME = "Moscow"
    }
}