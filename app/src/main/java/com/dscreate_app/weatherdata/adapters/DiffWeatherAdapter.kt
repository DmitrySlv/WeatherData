package com.dscreate_app.weatherdata.adapters

import androidx.recyclerview.widget.DiffUtil
import com.dscreate_app.weatherdata.models.WeatherModelDto

object DiffWeatherAdapter: DiffUtil.ItemCallback<WeatherModelDto>(){

    override fun areItemsTheSame(oldItem: WeatherModelDto, newItem: WeatherModelDto): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WeatherModelDto, newItem: WeatherModelDto): Boolean {
        return oldItem == newItem
    }
}