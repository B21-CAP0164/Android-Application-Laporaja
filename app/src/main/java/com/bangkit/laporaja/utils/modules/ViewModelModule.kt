package com.bangkit.laporaja.utils

import com.bangkit.laporaja.viewmodels.HistoryViewModel
import com.bangkit.laporaja.viewmodels.HomeViewModel
import com.bangkit.laporaja.viewmodels.ReportDetailViewModel
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
}