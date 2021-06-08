@file:Suppress("unused")

package com.bangkit.laporaja

import android.app.Application
import com.bangkit.laporaja.utils.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LaporAjaApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LaporAjaApp)
            modules(
                listOf(
                    remoteDataSourceModule,
                    repositoryModule,
                    viewModelModule,
                    retrofitModule,
                    apiModule
                )
            )
        }
    }
}