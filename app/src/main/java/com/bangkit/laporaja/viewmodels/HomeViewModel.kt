package com.bangkit.laporaja.viewmodels

import androidx.lifecycle.ViewModel
import com.bangkit.laporaja.data.LaporAjaRepositoryInterface
import com.bangkit.laporaja.data.entity.Report
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repos: LaporAjaRepositoryInterface) : ViewModel() {
    fun getRecentReports(): Flow<List<Report>> = repos.getRecentReports()
}