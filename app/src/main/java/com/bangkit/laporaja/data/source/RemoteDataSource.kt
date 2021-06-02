package com.bangkit.laporaja.data.source

import android.util.Log
import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem
import com.bangkit.laporaja.data.source.api.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService) {
    private val format = "json"

    fun getRecentReports() : Flow<List<ReportListResponseItem>> = flow {
        try {
            val response = api.getLatestReports(format)

            if (!response.isNullOrEmpty()) {
                emit(response)
            } else {
                emit(ArrayList<ReportListResponseItem>())
            }
        } catch (e: Exception) {
            logError(e, "getRecentReports")
        }
    }

    fun getUserReports(userId: String) : Flow<List<ReportListResponseItem>> = flow {
        try {
            val response = api.getUserReports(userId, format)

            if (!response.isNullOrEmpty()) {
                emit(response)
            } else {
                emit(ArrayList<ReportListResponseItem>())
            }
        } catch (e: Exception) {
            logError(e, "getUserReports")
        }
    }

    fun getReportDetail(userId: String, reportId: Long) : Flow<ReportResponseItem> = flow {
        try {
            val response = api.getReportDetails(userId, reportId, format)

            if (!response.isNullOrEmpty()) {
                emit(response[0])
            } else {
                emit(ReportResponseItem(null, null, null, null, null, null))
            }
        } catch (e: Exception) {
            logError(e, "getReportDetail")
        }
    }

    private fun logError(e: Any, note: String) {
        Log.d(note, e.toString())
    }
}