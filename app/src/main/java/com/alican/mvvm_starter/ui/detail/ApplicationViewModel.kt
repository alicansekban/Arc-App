package com.alican.mvvm_starter.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.domain.repository.DataRepository
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ApplicationViewModel @Inject constructor(private val webService: WebService,private val db:AppDatabase, val repository: DataRepository) : ViewModel() {

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

    fun getFlashLightsFromDb(searchQuery : String = "") : Flow<List<FlashLightsEntity>>{
      return  db.flashLightsDao().getAllData(searchQuery).transform {
          val list = it
          emit(list)
      }
    }

}