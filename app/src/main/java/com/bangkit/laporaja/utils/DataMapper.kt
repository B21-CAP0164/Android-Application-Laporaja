package com.bangkit.laporaja.utils

import com.bangkit.laporaja.data.entity.Report
import com.bangkit.laporaja.data.post.DataInputPost
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
        damageSeverity = it.damageSeverity,
        date = it.createdAt
    )

    fun mapReportToDataInput(it: Report) = DataInputPost(
        locationString = it.location,
        lat = it.latitude,
        lon = it.longitude,
        image = it.photo,
        notes = it.description,
        damageSeverity = it.damageSeverity
    )
}