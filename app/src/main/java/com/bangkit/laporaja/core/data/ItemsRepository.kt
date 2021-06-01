package com.bangkit.laporaja.core.data

import androidx.lifecycle.LiveData
import com.bangkit.laporaja.core.data.source.UserEntity


class ItemsRepository:DataSourceLaporAja {

    override fun getUser(): LiveData<UserEntity> {
        TODO("Not yet implemented")
    }
}