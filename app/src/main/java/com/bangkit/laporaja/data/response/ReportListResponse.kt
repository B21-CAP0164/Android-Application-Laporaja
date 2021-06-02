package com.bangkit.laporaja.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReportListResponse(

	@field:SerializedName("response")
	val response: List<ReportListResponseItem?>? = null
) : Parcelable

@Parcelize
data class ReportListResponseItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("location_name")
	val locationName: String? = null,

	@field:SerializedName("user_id")
	val userId: Long? = null,

	@field:SerializedName("id")
	val id: Long? = null
) : Parcelable
