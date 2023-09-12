package com.dscreate_app.weatherdata.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dscreate_app.weatherdata.R
import com.dscreate_app.weatherdata.databinding.ListItemBinding
import com.dscreate_app.weatherdata.models.WeatherModelDto
import com.squareup.picasso.Picasso

class WeatherAdapter(
   private val listener: Listener?
): ListAdapter<WeatherModelDto, WeatherAdapter.Holder>(DiffWeatherAdapter) {

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
        private var itemTemp: WeatherModelDto? = null

        init {
            itemView.setOnClickListener {
                itemTemp?.let { itemTemp -> listener?.onClick(itemTemp) }
            }
        }

        fun setData(item: WeatherModelDto) = with(binding) {
            itemTemp = item
            tvDate.text = item.time
            tvCondition.text = item.condition
            tvTemp.text = if (item.currentTemp.isEmpty()) {
                "${item.maxTemp} C° / ${item.minTemp} C°"
            } else {
                item.currentTemp + " C°"
            }
            Picasso.get().load("https:" + item.image).into(imView)
        }
    }

    interface Listener {
        fun onClick(item: WeatherModelDto)
    }
}