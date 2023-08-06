package com.y.medicinesound_compose.data.repositoryImpls

import com.y.medicinesound_compose.data.remote.datasource.EYakService
import com.y.medicinesound_compose.data.remote.dto.EYakItem
import com.y.medicinesound_compose.domain.repositories.EYakRepo
import java.lang.RuntimeException
import javax.inject.Inject

class EYakRepoImpl @Inject constructor(
    private val eYakService: EYakService,
) : EYakRepo {

    override suspend fun getEYakList(itemName: String): Result<List<EYakItem>> {
        return try {
            val response = eYakService.getEYakList(itemName = itemName)
            if (response.isSuccessful) {
                //Log.d("EYakRepoImpl", "getEYakList: response is successful")
                Result.success(response.body()?.body?.items ?: emptyList())
            } else {
                //Log.d("EYakRepoImpl", "getEYakList: response is not successful")
                Result.failure(RuntimeException("Response is not successful"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}