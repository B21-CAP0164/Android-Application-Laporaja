package com.bangkit.laporaja.data.source

import androidx.lifecycle.LiveData
import com.bangkit.laporaja.data.entity.User

interface DataSourceLaporAja {
    fun getUser(): LiveData<User>
}