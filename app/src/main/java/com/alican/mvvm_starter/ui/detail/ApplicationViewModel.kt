package com.alican.mvvm_starter.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(private val webService: WebService) : ViewModel() {

    val flashLightsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()
    val colorLightsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()
    val sosAlertsResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()

    fun getFlashLights() {
        flashLightsResponse.request({ webService.getFlashLights() })
    }

    fun getColorLights() {
        colorLightsResponse.request({ webService.getColorLights() })
    }

    fun getSosAlerts() {
        sosAlertsResponse.request({ webService.getSosAlerts() })
    }

}