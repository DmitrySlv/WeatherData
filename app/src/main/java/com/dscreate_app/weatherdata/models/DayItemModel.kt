package com.dscreate_app.weatherdata.models

data class DayItemModel(
    val region: String,
    val city: String,
    val date: String,
    val condition: String,
    val imageUrl: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val wind_kph: Float,
    val humidity: Int,
    val cloud: Int,
    val hours: String
)
