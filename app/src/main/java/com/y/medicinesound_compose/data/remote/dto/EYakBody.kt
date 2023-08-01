package com.y.medicinesound_compose.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EYakBody(
    @field:Json(name = "pageNo") val pageNo: Int,
    @field:Json(name = "totalCount") val totalCount: Int,
    @field:Json(name = "numOfRows") val numOfRows: Int,
    @field:Json(name = "items") val items: List<EYakItem>
)
