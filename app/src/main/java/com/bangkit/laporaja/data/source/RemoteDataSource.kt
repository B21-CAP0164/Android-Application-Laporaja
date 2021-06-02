package com.bangkit.laporaja.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.entity.User
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
            val dataArray = response.response

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<ReportListResponseItem>)
            } else {
                emit(ArrayList<ReportListResponseItem>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getUserReports(userId: String) : Flow<List<ReportListResponseItem>> = flow {
        try {
            val response = api.getUserReports(userId, format)
            val dataArray = response.response

            if (!dataArray.isNullOrEmpty()) {
                emit(dataArray as List<ReportListResponseItem>)
            } else {
                emit(ArrayList<ReportListResponseItem>())
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    fun getReportDetail(userId: String, reportId: Long) : Flow<ReportResponseItem> = flow {
        try {
            val response = api.getReportDetails(userId, reportId, format)
            val data = response.reportResponse

            if (data != null) {
                emit(data)
            } else {
                emit(ReportResponseItem(null, null, null, null, null, null))
            }
        } catch (e: Exception) {
            logError(e)
        }
    }

    private fun logError(e: Exception) {
        Log.d("RemoteDataSource", e.toString())
    }
}