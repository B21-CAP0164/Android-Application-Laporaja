package com.bangkit.laporaja.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportListResponseItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("location_name")
    val locationName: String? = null,

    @field:SerializedName("google_id")
    val googleId: String? = null,

    @field:SerializedName("id")
    val id: Long? = null
) : Parcelable
