package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.source.RemoteDataSource
import com.bangkit.laporaja.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LaporAjaRepository(private val dataSource: RemoteDataSource) : LaporAjaRepositoryInterface {
    override fun getRecentReports(): Flow<List<Report>> {
        return dataSource.getRecentReports().map {
            DataMapper.mapReportListResponseToReportList(it)
        }
    }

    override fun getUserReports(userId: Long): Flow<List<Report>> {
        return dataSource.getUserReports(userId).map {
            DataMapper.mapReportListResponseToReportList(it)
        }
    }

    override fun getReportDetails(userId: Long, reportId: Long): Flow<Report> {
        return dataSource.getReportDetail(userId, reportId).map {
            DataMapper.mapReportResponseToReport(it)
        }
    }
}