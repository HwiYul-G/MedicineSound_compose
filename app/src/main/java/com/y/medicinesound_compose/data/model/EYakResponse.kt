package com.y.medicinesound_compose.data.model

import com.google.gson.annotations.SerializedName


data class EYakResponse(
    @SerializedName("header") val header : EYakHeader,
    @SerializedName("body") val body : EYakBody
)
