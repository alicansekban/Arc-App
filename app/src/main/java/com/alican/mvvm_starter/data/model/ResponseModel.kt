package com.alican.mvvm_starter.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseModel(

	@Json(name="developerAddress")
	val developerAddress: String? = null,

	@Json(name="publishDate")
	val publishDate: PublishDate? = null,

	@Json(name="developerEmail")
	val developerEmail: String? = null,

	@Json(name="ratingCount")
	val ratingCount: Int? = null,

	@Json(name="version")
	val version: String? = null,

	@Json(name="downloads")
	val downloads: String? = null,

	@Json(name="price")
	val price: String? = null,

	@Json(name="ratingValue")
	val ratingValue: Double? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="packageName")
	val packageName: String? = null,

	@Json(name="iconUrl")
	val iconUrl: String? = null,

	@Json(name="category")
	val category: String? = null,

	@Json(name="developerName")
	val developerName: String? = null
) : Parcelable {

	fun convertRatingToString():String {
		return ratingValue.toString()
	}
}

@Parcelize
data class PublishDate(

	@Json(name="month")
	val month: Int? = null,

	@Json(name="year")
	val year: Int? = null,

	@Json(name="day")
	val day: Int? = null
) : Parcelable
