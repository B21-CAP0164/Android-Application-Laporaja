package com.bangkit.laporaja.data.post

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataInputPost(
    @SerializedName("location_name")
    @Expose
    val locationString: String?,

    @SerializedName("image")
    @Expose
    val image: String?,

    @SerializedName("notes")
    @Expose
    val notes: String?,

    @SerializedName("lat")
    @Expose
    val lat: Float?,

    @SerializedName("long")
    @Expose
    val lon: Float?,

    @SerializedName("damage_severity")
    @Expose
    val damageSeverity: String?,
) : Parcelable
