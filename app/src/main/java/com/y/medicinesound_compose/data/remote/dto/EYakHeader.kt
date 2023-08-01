package com.y.medicinesound_compose.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EYakHeader(
    @Json(name = "resultCode") val resultCode: String,
    @Json(name = "resultMsg") val resultMsg: String
)
