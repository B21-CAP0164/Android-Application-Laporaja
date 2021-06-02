package com.bangkit.laporaja.utils

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.response.ReportListResponseItem
import com.bangkit.laporaja.data.response.ReportResponseItem

object DataMapper {
    fun mapReportListResponseToReportList(input: List<ReportListResponseItem>) = input.map {
        Report(
            id = it.id,
            userId = it.userId,
            photo = it.image,
            location = it.locationName,
        )
    }

    fun mapReportResponseToReport(it: ReportResponseItem) = Report(
        id = it.id,
        userId = it.user,
        photo = it.image,
        location = it.locationName,
        latitude = it.lat?.toFloat(),
        longitude = it.lon?.toFloat(),
        description = it.notes,
        damageType = it.damageType,
        damageSeverity = it.damageSeverity,
        date = it.createdAt
    )
}