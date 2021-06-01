package com.bangkit.laporaja.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report (
    val id: Int,
    val userId: Int,
    val photo: String,
    val location: String,
    val latitude: Float,
    val longitude: Float,
    val description: String?,
    val damageType: String?,
    val damageSeverity: String?,
    val date: String?
) : Parcelable