package com.y.medicinesound_compose.domain.usecases

import android.content.Context
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import javax.inject.Inject

class CaptureAndSaveImageUseCase @Inject constructor(
    private val customCameraRepo: CustomCameraRepo
) {
    suspend fun captureAndSaveImage(context: Context) {
        customCameraRepo.captureAndSaveImage(context)
    }
}