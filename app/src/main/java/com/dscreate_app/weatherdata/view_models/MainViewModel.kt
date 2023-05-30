package com.dscreate_app.weatherdata.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dscreate_app.weatherdata.models.WeatherModel

class MainViewModel: ViewModel() {

    private val _liveDataCurrent = MutableLiveData<WeatherModel>()
    val liveDataCurrent: LiveData<WeatherModel>
        get() = _liveDataCurrent

    private val _liveDataList = MutableLiveData<List<WeatherModel>>()
    val liveDataList: LiveData<List<WeatherModel>>
        get() = _liveDataList


     fun updateCurrentData(weatherModel: WeatherModel) {
        _liveDataCurrent.value = weatherModel
    }

    fun updateDataList(list: List<WeatherModel>) {
        _liveDataList.value = list
    }

}