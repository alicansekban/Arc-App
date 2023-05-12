package com.alican.mvvm_starter.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.alican.mvvm_starter.data.local.model.ColorsLightsEntity
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorLightsDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertData(dataModel: List<ColorsLightsEntity>)


    @Query("SELECT * FROM colorlights WHERE name LIKE '%' || :searchQuery || '%'" )
    fun getAllData(searchQuery:String) : Flow<List<ColorsLightsEntity>>

    @Query("Select Count(*) from colorlights ")
    suspend fun getColorLightsCount() : Int
}
