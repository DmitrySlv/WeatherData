package com.dscreate_app.weatherdata.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dscreate_app.weatherdata.databinding.ListItemBinding
import com.dscreate_app.weatherdata.models.WeatherModel

class WeatherAdapter: ListAdapter<WeatherModel, ViewHolder>(DiffWeatherAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
       private val binding = ListItemBinding.bind(view)
        fun setData(weatherModel: WeatherModel) = with(binding) {
            tvDate.text = weatherModel.time
            tvCondition.text = weatherModel.condition
            tvTemp.text = weatherModel.currentTemp
        }
    }
}