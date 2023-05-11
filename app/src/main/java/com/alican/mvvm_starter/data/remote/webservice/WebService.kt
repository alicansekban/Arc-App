package com.alican.mvvm_starter.data.remote.webservice

import com.alican.mvvm_starter.base.BasePagingResponse
import com.alican.mvvm_starter.data.remote.dto.GetDataDto
import com.alican.mvvm_starter.base.BaseResponse
import com.alican.mvvm_starter.data.model.*
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("flashlights")
    suspend fun getFlashLights(): BaseResponse<List<ResponseModel>>

    @GET("colorlights")
    suspend fun getColorLights(): BaseResponse<List<ResponseModel>>

    @GET("sosalerts")
    suspend fun getSosAlerts(): BaseResponse<List<ResponseModel>>
}
