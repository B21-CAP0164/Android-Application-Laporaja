package com.bangkit.laporaja.data.post

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionPost(
	@SerializedName("data")
	@Expose
	val data: String? = null
) : Parcelable
