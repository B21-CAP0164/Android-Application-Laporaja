package com.bangkit.laporaja.data.source.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id:String? = null,
    var name:String? = null,
    var email:String? = null,
    var images:String? = null
):Parcelable
