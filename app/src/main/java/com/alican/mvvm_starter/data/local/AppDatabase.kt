package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.mvvm_starter.data.local.dao.ColorLightsDao
import com.alican.mvvm_starter.data.local.dao.FlashLightsDao
import com.alican.mvvm_starter.data.local.dao.SosAlertsDao
import com.alican.mvvm_starter.data.local.model.ColorsLightsEntity
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity
import com.alican.mvvm_starter.data.local.model.SosAlertsEntity

@Database(
    entities = [FlashLightsEntity::class, ColorsLightsEntity::class, SosAlertsEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashLightsDao(): FlashLightsDao
    abstract fun colorLightsDao(): ColorLightsDao
    abstract fun sosAlertsDao(): SosAlertsDao
}
