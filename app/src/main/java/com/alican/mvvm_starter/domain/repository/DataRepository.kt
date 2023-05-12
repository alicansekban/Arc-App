package com.alican.mvvm_starter.domain.repository

import android.content.Context
import com.alican.mvvm_starter.data.local.AppDatabase
import com.alican.mvvm_starter.data.local.model.ColorsLightsEntity
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.local.model.SosAlertsEntity
import com.alican.mvvm_starter.data.remote.webservice.WebService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val webService: WebService,
    private val db: AppDatabase,
    @ApplicationContext context: Context
) {

    suspend fun fetchData() {
        withContext(Dispatchers.IO) {
            db.flashLightsDao().getFlashLightsCount().takeIf { it == 0 }?.let {
                val dataList = webService.getFlashLights()
                val convertedFlashLightsList = mutableListOf<FlashLightsEntity>()
                if (dataList.isSuccessful) {
                    dataList.body()?.let {
                        it.forEach {
                            convertedFlashLightsList.add(
                                FlashLightsEntity(
                                    ratingCount = it.ratingCount!!,
                                    downloads = it.downloads.toString(),
                                    ratingValue = it.ratingValue!!,
                                    name = it.name.toString(),
                                    packageName = it.packageName.toString(),
                                    iconUrl = it.iconUrl.toString()
                                )
                            )
                        }
                    }
                    db.flashLightsDao().insertData(convertedFlashLightsList)
                }
            }

        }
        withContext(Dispatchers.IO) {
            db.colorLightsDao().getColorLightsCount().takeIf { it == 0 }?.let {
                val colorsList = webService.getColorLights()
                val convertedColorList = mutableListOf<ColorsLightsEntity>()
                if (colorsList.isSuccessful) {
                    colorsList.body()?.let {
                        it.forEach {
                            convertedColorList.add(
                                ColorsLightsEntity(
                                    ratingCount = it.ratingCount!!,
                                    downloads = it.downloads.toString(),
                                    ratingValue = it.ratingValue!!,
                                    name = it.name.toString(),
                                    packageName = it.packageName.toString(),
                                    iconUrl = it.iconUrl.toString()
                                )
                            )
                        }
                        db.colorLightsDao().insertData(convertedColorList)
                    }
                }

            }
        }

        withContext(Dispatchers.IO) {
            db.sosAlertsDao().getSosAlertsCount().takeIf { it == 0 }?.let {
                val colorsList = webService.getSosAlerts()
                val convertedSosAlertsList = mutableListOf<SosAlertsEntity>()
                if (colorsList.isSuccessful) {
                    colorsList.body()?.let {
                        it.forEach {
                            convertedSosAlertsList.add(
                                SosAlertsEntity(
                                    ratingCount = it.ratingCount!!,
                                    downloads = it.downloads.toString(),
                                    ratingValue = it.ratingValue!!,
                                    name = it.name.toString(),
                                    packageName = it.packageName.toString(),
                                    iconUrl = it.iconUrl.toString()
                                )
                            )
                        }
                    }
                    db.sosAlertsDao().insertData(convertedSosAlertsList)
                }
            }
        }
    }

    suspend fun refreshData(type: String) {

    }

    fun fetchFlashLightsFromDb(searchQuery: String): Flow<List<FlashLightsEntity>> {
        return db.flashLightsDao().getAllData(searchQuery).transform {
            emit(it)
        }
    }

    fun fetchColorLightsFromDb(searchQuery: String): Flow<List<ColorsLightsEntity>> {
        return db.colorLightsDao().getAllData(searchQuery).transform {
            emit(it)
        }
    }

    fun fetchSosAlertsFromDb(searchQuery: String): Flow<List<SosAlertsEntity>> {
        return db.sosAlertsDao().getAllData(searchQuery).transform {
            emit(it)
        }
    }
}