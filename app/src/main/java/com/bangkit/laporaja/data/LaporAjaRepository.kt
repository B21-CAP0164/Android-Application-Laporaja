package com.bangkit.laporaja.data

import androidx.lifecycle.LiveData
import com.bangkit.laporaja.data.entity.User
import com.bangkit.laporaja.data.source.DataSourceLaporAja


class LaporAjaRepository: DataSourceLaporAja {

    override fun getUser(): LiveData<User> {
        TODO("Not yet implemented")
    }
}