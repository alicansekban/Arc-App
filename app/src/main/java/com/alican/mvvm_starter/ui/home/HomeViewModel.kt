package com.alican.mvvm_starter.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.murgupluoglu.request.RESPONSE
import com.murgupluoglu.request.request
import com.alican.mvvm_starter.data.remote.webservice.WebService
import com.alican.mvvm_starter.util.SingleLiveEvent
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.model.ResponseModel
import com.alican.mvvm_starter.domain.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val webService: WebService,
    private val repository: DataRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.fetchData()
        }
    }

}
