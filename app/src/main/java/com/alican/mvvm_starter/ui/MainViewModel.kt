package com.alican.mvvm_starter.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val webService: WebService,
    private val db: AppDatabase
) : ViewModel() {

    val flashLightsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()
    val colorLightsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()
    val sosAlertsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()

    fun getFlashLights() {
        viewModelScope.launch(Dispatchers.IO) {
            db.flashLightsDao().getSatelliteCount().takeIf { it == 0 }.let {
                flashLightsResponse.request({ webService.getFlashLights()})
            }

        }
    }

    fun getColorLights() {
        colorLightsResponse.request({ webService.getColorLights() })
    }

    fun getSosAlerts() {
        sosAlertsResponse.request({ webService.getSosAlerts() })
    }

    fun insertFlashLightsDataToDb(items:List<FlashLightsEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            db.flashLightsDao().insertData(items)
        }
    }
}