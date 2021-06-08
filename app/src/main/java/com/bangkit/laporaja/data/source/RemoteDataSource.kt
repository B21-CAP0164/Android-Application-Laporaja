package com.bangkit.laporaja.data.source

import android.util.Log
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.post.PredictionPost
import com.bangkit.laporaja.data.response.PredictionResponse
import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem
import com.bangkit.laporaja.data.source.api.ApiService
import com.bangkit.laporaja.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val api: ApiService) {
    private val format = "json"

    fun getRecentReports(): Flow<List<ReportListResponseItem>> = flow {
        val response = api.getLatestReports(format)

        if (!response.isNullOrEmpty()) {
            emit(response)
        } else {
            emit(ArrayList<ReportListResponseItem>())
        }
    }.catch { e ->
        logError(e, "getRecentReports")
        emit(ArrayList<ReportListResponseItem>())
    }

    fun getUserReports(userId: String): Flow<List<ReportListResponseItem>> = flow {
        val response = api.getUserReports(userId, format)

        if (!response.isNullOrEmpty() && response[0].id != null) {
            emit(response)
        } else {
            emit(ArrayList<ReportListResponseItem>())
        }
    }.catch { e ->
        logError(e, "getUserReports")
        emit(ArrayList<ReportListResponseItem>())
    }

    fun getReportDetail(userId: String, reportId: Long): Flow<ReportResponseItem> = flow {
        val response = api.getReportDetails(userId, reportId, format)

        if (response.id != null) {
            emit(response)
        } else {
            emit(ReportResponseItem(id = null))
        }
    }.catch { e ->
        logError(e, "getReportDetail")
    }

    fun sendDataToPredict(base64: String): Flow<PredictionResponse> = flow {
        val data = PredictionPost(base64)
        val response = api.postImageReturnPrediction(data)

        if (response.error == null) {
            emit(response)
        } else {
            val errorPred = PredictionResponse(
                prediction = "error",
                error = response.error
            )

            emit(errorPred)
        }

    }.catch { e ->
        logError(e, "SendDataToPredict")
    }

    fun sendDataToCloud(data: Report, id: String): Flow<Long> = flow {
        val input = DataMapper.mapReportToDataInput(data)
        val response = api.postDataToCloud(input, id, format)

        emit(response.id)
    }.catch { e ->
        logError(e, "SendDataToCloud")
    }

    private fun logError(e: Any, note: String) {
        Log.d(note, e.toString())
    }
}