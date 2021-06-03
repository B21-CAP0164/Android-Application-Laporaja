package com.bangkit.laporaja.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report (
    val id: Long? = null,
    val userId: String? = null,
    val photo: String? = null,
    val location: String? = null,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val description: String? = null,
    val damageSeverity: String? = null,
    val date: String? = null
) : Parcelable