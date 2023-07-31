package com.y.medicinesound_compose.domain.usecases

import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import javax.inject.Inject

class GetCameraPreviewUseCase @Inject constructor(
    private val customCameraRepo: CustomCameraRepo,
) {
    suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
    ){
        customCameraRepo.showCameraPreview(previewView, lifecycleOwner)
    }
}