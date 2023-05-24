package com.dscreate_app.weatherdata.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val _liveDataCurrent = MutableLiveData<String>()
    val liveDataCurrent: LiveData<String>
        get() = _liveDataCurrent

    private val _liveDataList = MutableLiveData<String>()
    val liveDataList: LiveData<String>
        get() = _liveDataList
}