package com.y.medicinesound_compose.domain.repositories

import com.y.medicinesound_compose.data.remote.dto.EYakItem

interface EYakRepo {

    suspend fun getEYakList(itemName : String) : Result<List<EYakItem>>

}