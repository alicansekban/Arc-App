package com.alican.mvvm_starter.data.local.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "colorlights")
@Parcelize
data class ColorsLightsEntity(
    @PrimaryKey(autoGenerate = true)
    var ratingCount: Int,
    var downloads: String,
    var ratingValue: Double,
    var name: String,
    var packageName: String,
    var iconUrl : String
) : Parcelable {

    fun convertRatingToString():String {
        return ratingValue.toString()
    }
}
