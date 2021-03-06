package com.bangkit.laporaja.utils.modules

import com.bangkit.laporaja.viewmodels.HistoryViewModel
import com.bangkit.laporaja.viewmodels.HomeViewModel
import com.bangkit.laporaja.viewmodels.ReportDetailViewModel
import com.bangkit.laporaja.viewmodels.SendDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        HistoryViewModel(get())
    }

    viewModel {
        ReportDetailViewModel(get())
    }

    viewModel {
        SendDataViewModel(get())
    }
}