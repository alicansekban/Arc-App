package com.alican.mvvm_starter.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashLightsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertData(dataModel: List<FlashLightsEntity>)


    @Query("SELECT * FROM flashlights WHERE name LIKE '%' || :searchQuery || '%'" )
    fun getAllData(searchQuery:String) : Flow<List<FlashLightsEntity>>

    @Query("Select Count(*) from flashlights ")
    suspend fun getSatelliteCount() : Int
}
