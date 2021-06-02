package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import kotlinx.coroutines.flow.Flow

interface LaporAjaRepositoryInterface {
    fun getRecentReports(): Flow<List<Report>>
    fun getUserReports(userId: String) : Flow<List<Report>>
    fun getReportDetails(userId: String, reportId: Long) : Flow<Report>
    fun getUserReportsCount(userId: String) : Flow<Int>
}