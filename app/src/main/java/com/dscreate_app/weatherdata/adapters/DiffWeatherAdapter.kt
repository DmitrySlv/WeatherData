package com.dscreate_app.weatherdata.adapters

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.dscreate_app.weatherdata.models.WeatherModel

object DiffWeatherAdapter: ItemCallback<WeatherModel>() {
    override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
        return oldItem == newItem
    }
}