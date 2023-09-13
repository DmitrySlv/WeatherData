package com.dscreate_app.weatherdata.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dscreate_app.weatherdata.models.WeatherModelDto

class MainViewModel: ViewModel() {

    val liveDataCurrent = MutableLiveData<WeatherModelDto>()
    val liveDataList = MutableLiveData<List<WeatherModelDto>>()
}