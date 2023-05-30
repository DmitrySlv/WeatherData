package com.dscreate_app.weatherdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dscreate_app.weatherdata.R
import com.dscreate_app.weatherdata.databinding.ListItemBinding
import com.dscreate_app.weatherdata.models.WeatherModel
import com.squareup.picasso.Picasso

class WeatherAdapter(private val listener: Listener?):
    ListAdapter<WeatherModel, WeatherAdapter.Holder>(DiffWeatherAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(getItem(position))
    }

    class Holder(view: View, private val listener: Listener?): ViewHolder(view) {
       private val binding = ListItemBinding.bind(view)
        private var itemTemp: WeatherModel? = null

        init {
            itemView.setOnClickListener {
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        fun setData(weatherModel: WeatherModel) = with(binding) {
            itemTemp = weatherModel
            tvDate.text = weatherModel.time
            tvCondition.text = weatherModel.condition
            tvTemp.text = weatherModel.currentTemp.ifEmpty {
                "${weatherModel.maxTemp}°C / ${weatherModel.minTemp}°C"
            }
            Picasso.get().load("https:" + weatherModel.imageUrl).into(imView)
        }
    }

    interface Listener {
        fun onClick(item: WeatherModel)
    }
}