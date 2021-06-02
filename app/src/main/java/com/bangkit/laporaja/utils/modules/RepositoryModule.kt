package com.bangkit.laporaja.utils.modules

import com.bangkit.laporaja.data.LaporAjaRepository
import com.bangkit.laporaja.data.LaporAjaRepositoryInterface
import org.koin.dsl.module

val repositoryModule = module {
    single<LaporAjaRepositoryInterface> {
        LaporAjaRepository(get())
    }
}