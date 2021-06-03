package com.bangkit.laporaja.data.post

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Response(
	@field:SerializedName("instances")
	val instances: List<InstancesItem?>? = null
) : Parcelable

@Parcelize
data class InstancesItem(
	@field:SerializedName("b64")
	val b64: String? = null
) : Parcelable
