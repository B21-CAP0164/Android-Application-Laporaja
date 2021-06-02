package com.bangkit.laporaja.utils.modules

import com.bangkit.laporaja.data.source.RemoteDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single {
        RemoteDataSource(get())
    }
}