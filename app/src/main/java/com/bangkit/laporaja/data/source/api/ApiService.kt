package com.bangkit.laporaja.data.source.api

import com.bangkit.laporaja.data.post.DataInputPost
import com.bangkit.laporaja.data.post.PredictionPost
import com.bangkit.laporaja.data.response.InsertResponse
import com.bangkit.laporaja.data.response.PredictionResponse
import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem
import retrofit2.http.*

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

    @POST("https://ml.googleapis.com/v1/projects/gold-order-314913/models/laporaja_model/versions/v1modelkeras:predict")
    suspend fun postImageReturnPrediction(
        @Body data: PredictionPost
    ): PredictionResponse

    @POST("report/{id}/add")
    suspend fun postDataToCloud(
        @Body data: DataInputPost,
        @Path("id") id: String?
    ): InsertResponse
}