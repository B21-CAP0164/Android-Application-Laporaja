package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import kotlinx.coroutines.flow.Flow

interface LaporAjaRepositoryInterface {
    fun getRecentReports(): Flow<List<Report>>
    fun getUserReports(userId: Int) : Flow<List<Report>>
    fun getReportDetails(userId: Int, reportId: Int) : Flow<Report>
}