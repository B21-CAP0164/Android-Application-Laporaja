package com.bangkit.laporaja.data.source.api

import com.bangkit.laporaja.data.entity.Report
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("report/")
    fun getLatestReports(
        @Query("format") format: String
    ) : Call<Report>

    @GET("report/{id}")
    fun getUserReports(
        @Path("id") id: Int,
        @Query("format") format: String
    )

    @GET("report/{id}/{report_id}")
    fun getReportDetails(
        @Path("id") id: Int,
        @Path("report_id") reportId: Int,
        @Query("format") format: String
    )
}