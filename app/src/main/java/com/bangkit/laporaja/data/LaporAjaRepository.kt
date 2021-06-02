package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.source.RemoteDataSource
import com.bangkit.laporaja.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

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
}