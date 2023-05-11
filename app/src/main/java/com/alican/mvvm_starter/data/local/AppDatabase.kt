package com.alican.mvvm_starter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alican.mvvm_starter.data.local.model.FlashLightsEntity

@Database(entities = [FlashLightsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashLightsDao(): FlashLightsDao
}
