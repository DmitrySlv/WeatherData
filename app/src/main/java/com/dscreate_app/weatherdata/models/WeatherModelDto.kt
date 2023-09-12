package com.dscreate_app.weatherdata.models

data class WeatherModelDto(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val minTemp: String,
    val maxTemp: String,
    val image: String,
    val hours: String
)
