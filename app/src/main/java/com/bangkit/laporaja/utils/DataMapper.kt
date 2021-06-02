package com.bangkit.laporaja.utils

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem

object DataMapper {
    fun mapReportListResponseToReportList(input: List<ReportListResponseItem>) = input.map {
        Report(
            id = it.id as Int,
            userId = it.userId as Int,
            photo = it.image as String,
            location = it.locationName as String,
        )
    }

    fun mapReportResponseToReport(it: ReportResponseItem) = Report(
        id = it.id as Int,
        userId = it.user as Int,
        photo = it.image as String,
        location = it.locationName as String,
        latitude = it.lat?.toFloat() as Float,
        longitude = it.lon?.toFloat() as Float,
        description = it.notes,
        damageType = it.damageType,
        damageSeverity = it.damageSeverity,
        date = it.createdAt
    )
}