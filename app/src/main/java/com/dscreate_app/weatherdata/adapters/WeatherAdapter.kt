package com.dscreate_app.weatherdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dscreate_app.weatherdata.R
import com.dscreate_app.weatherdata.databinding.ListItemBinding
import com.dscreate_app.weatherdata.models.WeatherModel

class WeatherAdapter: ListAdapter<WeatherModel, WeatherAdapter.Holder>(DiffWeatherAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(getItem(position))
    }

    class Holder(view: View): ViewHolder(view) {
       private val binding = ListItemBinding.bind(view)

        fun setData(weatherModel: WeatherModel) = with(binding) {
            tvDate.text = weatherModel.time
            tvCondition.text = weatherModel.condition
            tvTemp.text = weatherModel.currentTemp
        }
    }
}