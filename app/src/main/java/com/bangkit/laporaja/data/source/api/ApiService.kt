package com.bangkit.laporaja.data.source.api

import com.bangkit.laporaja.data.response.ReportListResponse
import com.bangkit.laporaja.data.response.ReportResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("report/")
    suspend fun getLatestReports(
        @Query("format") format: String
    ): ReportListResponse

    @GET("report/{id}")
    suspend fun getUserReports(
        @Path("id") id: Long,
        @Query("format") format: String
    ): ReportListResponse

    @GET("report/{id}/{report_id}")
    suspend fun getReportDetails(
        @Path("id") id: Long,
        @Path("report_id") reportId: Long,
        @Query("format") format: String
    ): ReportResponse
}