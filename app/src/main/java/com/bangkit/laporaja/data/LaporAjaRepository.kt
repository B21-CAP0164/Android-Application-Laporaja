package com.bangkit.laporaja.data

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow


class LaporAjaRepository(private val dataSource: RemoteDataSource) : LaporAjaRepositoryInterface {
    override fun getRecentReports(): Flow<List<Report>> {
        TODO("Not yet implemented")
    }

    override fun getUserReports(userId: Int): Flow<List<Report>> {
        TODO("Not yet implemented")
    }

    override fun getReportDetails(userId: Int, reportId: Int): Flow<Report> {
        TODO("Not yet implemented")
    }

}