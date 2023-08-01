package com.y.medicinesound_compose.domain.usecases.EYak

import com.y.medicinesound_compose.data.remote.dto.EYakItem
import com.y.medicinesound_compose.domain.repositories.EYakRepo
import javax.inject.Inject

class GetEYakListUseCase @Inject constructor(
    private val eYakRepo: EYakRepo,
) {
    suspend operator fun invoke(itemName: String): Result<List<EYakItem>> {
        // Log.d("GetEYakListUseCase", "invoke called with itemName: $itemName")
        return eYakRepo.getEYakList(itemName)
    }

}