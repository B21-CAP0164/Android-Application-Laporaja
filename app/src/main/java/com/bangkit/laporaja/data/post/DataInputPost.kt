package com.bangkit.laporaja.data.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataInputPost(
    @SerializedName("location_name")
    val locationString: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("notes")
    val notes: String?,

    @SerializedName("lat")
    val lat: Float?,

    @SerializedName("long")
    val lon: Float?,

    @SerializedName("damage_severity")
    val damageSeverity: String?,
) : Parcelable
