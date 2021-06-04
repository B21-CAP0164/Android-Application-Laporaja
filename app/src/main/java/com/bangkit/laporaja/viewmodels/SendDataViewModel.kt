package com.bangkit.laporaja.viewmodels

import androidx.lifecycle.ViewModel
import com.bangkit.laporaja.data.LaporAjaRepositoryInterface
import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.response.PredictionResponse
import kotlinx.coroutines.flow.Flow

class SendDataViewModel(private val repos: LaporAjaRepositoryInterface) : ViewModel() {
    fun sendDataToPredict(base64: String): Flow<PredictionResponse> =
        repos.sendDataToPredict(base64)

    fun sendDataToCloud(data: Report): Flow<Long> = repos.sendDataToCloud(data)
}