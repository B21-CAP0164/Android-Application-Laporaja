package com.bangkit.laporaja.viewmodels

import androidx.lifecycle.ViewModel
import com.bangkit.laporaja.data.LaporAjaRepositoryInterface
import com.bangkit.laporaja.data.entity.Report
import kotlinx.coroutines.flow.Flow

class HistoryViewModel(private val repos: LaporAjaRepositoryInterface) : ViewModel() {
    fun getUserHistory(userId: String): Flow<List<Report>> = repos.getUserReports(userId)
}