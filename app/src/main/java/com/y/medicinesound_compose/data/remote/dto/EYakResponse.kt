package com.y.medicinesound_compose.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EYakResponse(
    @Json(name = "header") val header: EYakHeader,
    @Json(name = "body") val body: EYakBody
)
