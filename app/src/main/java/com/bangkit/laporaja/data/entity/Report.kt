package com.bangkit.laporaja.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report (
    val id: Long,
    val userId: String,
    val photo: String,
    val location: String,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val description: String? = null,
    val damageType: String? = null,
    val damageSeverity: String? = null,
    val date: String? = null
) : Parcelable