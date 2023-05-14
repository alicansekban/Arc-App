package com.alican.mvvm_starter.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val webService: WebService,
) : ViewModel() {

    val dataResponse: MutableLiveData<RESPONSE<Response<List<ResponseModel>>>> =
        MutableLiveData()


    fun getFlashLights() {
        dataResponse.request({ webService.getFlashLights() })
    }

    fun getColorLights() {
        dataResponse.request({ webService.getColorLights() })
    }

    fun getSosAlerts() {
        dataResponse.request({ webService.getSosAlerts() })
    }
}