package com.y.medicinesound_compose.data.model

import com.google.gson.annotations.SerializedName

data class EYakBody(
    @SerializedName("pageNo") val pageNo : Int,
    @SerializedName("totalCount") val totalCount : Int,
    @SerializedName("numOfRows") val numOfRows : Int,
    @SerializedName("items") val items : List<EYakItem>
)
