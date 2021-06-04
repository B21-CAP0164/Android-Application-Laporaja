package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.response.PredictionResponse
import kotlinx.coroutines.flow.Flow

interface LaporAjaRepositoryInterface {
    fun getRecentReports(): Flow<List<Report>>
    fun getUserReports(userId: String): Flow<List<Report>>
    fun getReportDetails(userId: String, reportId: Long): Flow<Report>
    fun getUserReportsCount(userId: String): Flow<Int>
    fun sendDataToPredict(base64: String): Flow<PredictionResponse>
    fun sendDataToCloud(data: Report): Flow<Long>
}