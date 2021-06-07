package com.bangkit.laporaja.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResponse(
    @field:SerializedName("prediction")
    val prediction : String?,
    @field:SerializedName("error")
    val error : String? = null
) : Parcelable