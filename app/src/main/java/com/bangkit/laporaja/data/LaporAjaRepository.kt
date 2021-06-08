package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.response.PredictionResponse
import com.bangkit.laporaja.data.source.RemoteDataSource
import com.bangkit.laporaja.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

class LaporAjaRepository(private val dataSource: RemoteDataSource) : LaporAjaRepositoryInterface {
    override fun getRecentReports(): Flow<List<Report>> {
        return dataSource.getRecentReports().map {
            DataMapper.mapReportListResponseToReportList(it)
        }
    }

    override fun getUserReports(userId: String): Flow<List<Report>> {
        return dataSource.getUserReports(userId).map {
            DataMapper.mapReportListResponseToReportList(it)
        }
    }

    override fun getReportDetails(userId: String, reportId: Long): Flow<Report> {
        return dataSource.getReportDetail(userId, reportId).map {
            DataMapper.mapReportResponseToReport(it)
        }
    }

    @ExperimentalCoroutinesApi
    override fun getUserReportsCount(userId: String): Flow<Int> = channelFlow {
        dataSource.getUserReports(userId).collectLatest {
            send(it.size)
        }
    }

    override fun sendDataToPredict(base64: String): Flow<PredictionResponse> {
        return dataSource.sendDataToPredict(base64)
    }

    override fun sendDataToCloud(data: Report): Flow<Long> {
        return dataSource.sendDataToCloud(data, data.googleId.toString())
    }
}