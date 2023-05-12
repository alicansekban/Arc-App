package com.alican.mvvm_starter.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.local.model.SosAlertsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SosAlertsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertData(dataModel: List<SosAlertsEntity>)


    @Query("SELECT * FROM sosalerts WHERE name LIKE '%' || :searchQuery || '%'" )
    fun getAllData(searchQuery:String) : Flow<List<SosAlertsEntity>>

    @Query("Select Count(*) from sosalerts ")
    suspend fun getSosAlertsCount() : Int
}
