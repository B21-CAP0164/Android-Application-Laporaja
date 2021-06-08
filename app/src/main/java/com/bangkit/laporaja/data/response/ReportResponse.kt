package com.bangkit.laporaja.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReportResponseItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("location_name")
    val locationName: String? = null,

    @field:SerializedName("notes")
    val notes: String? = null,

    @field:SerializedName("damage_severity")
    val damageSeverity: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("google_id")
    val googleId: String? = null,

    @field:SerializedName("lat")
    val lat: String? = null,

    @field:SerializedName("long")
    val lon: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("user_name")
    val name: String? = null,
) : Parcelable
