package com.y.medicinesound_compose.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class EYakItem(
    @Json(name = "entpName") val entpName: String,
    @Json(name = "itemName") val itemName: String,
    @Json(name = "itemSeq") val itemSeq: String,
    @Json(name = "efcyQesitm") val efcyQesitm: String,
    @Json(name = "useMethodQesitm") val useMethodQesitm: String? = null,
    @Json(name = "atpnWarnQesitm") val atpnWarnQesitm: String? =null,
    @Json(name = "atpnQesitm") val atpnQesitm: String? = null,
    @Json(name = "intrcQesitm") val intrcQesitm: String? = null,
    @Json(name = "seQesitm") val seQesitm: String? = null,
    @Json(name = "depositMethodQesitm") val depositMethodQesitm: String,
    @Json(name = "openDe") val openDe: String? = null,
    @Json(name = "updateDe") val updateDe: String? = null,
    @Json(name = "itemImage") val itemImage: String? = null,
    @Json(name = "bizrno") val bizrno: String,
)
