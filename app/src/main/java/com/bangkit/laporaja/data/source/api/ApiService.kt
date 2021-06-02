package com.bangkit.laporaja.data.source.api

import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("report/")
    suspend fun getLatestReports(
        @Query("format") format: String
    ): List<ReportListResponseItem>

    @GET("report/{id}")
    suspend fun getUserReports(
        @Path("id", encoded = true) id: String,
        @Query("format") format: String
    ): List<ReportListResponseItem>

    @GET("report/{id}/{report_id}")
    suspend fun getReportDetails(
        @Path("id") id: String,
        @Path("report_id") reportId: Long,
        @Query("format") format: String
    ): ReportResponseItem
}