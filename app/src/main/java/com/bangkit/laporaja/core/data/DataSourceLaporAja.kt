package com.bangkit.laporaja.core.data

import androidx.lifecycle.LiveData
import com.bangkit.laporaja.core.data.source.UserEntity

interface DataSourceLaporAja {
    fun getUser(): LiveData<UserEntity>
}