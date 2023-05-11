package com.alican.mvvm_starter.data.remote.webservice

import com.alican.mvvm_starter.data.model.ResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("flashlights")
    suspend fun getFlashLights(): Response<List<ResponseModel>>

    @GET("colorlights")
    suspend fun getColorLights(): Response<List<ResponseModel>>

    @GET("sosalerts")
    suspend fun getSosAlerts(): Response<List<ResponseModel>>
}
