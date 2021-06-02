package com.bangkit.laporaja.viewmodels

import androidx.lifecycle.ViewModel
import com.bangkit.laporaja.data.LaporAjaRepositoryInterface
import com.bangkit.laporaja.data.entity.Report
import kotlinx.coroutines.flow.Flow

class ReportDetailViewModel(private val repos: LaporAjaRepositoryInterface) : ViewModel() {
    fun getDetails(userId: String, reportId: Long): Flow<Report> =
        repos.getReportDetails(userId, reportId)
}