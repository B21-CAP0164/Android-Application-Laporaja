package com.bangkit.laporaja.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsertResponse(
    @field:SerializedName("id")
    val id: Long
) : Parcelable
