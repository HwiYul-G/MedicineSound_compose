package com.y.medicinesound_compose.domain.repositories

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface CustomCameraRepo {

    suspend fun captureAndSaveImage(context : Context)
    suspend fun showCameraPreview(
        previewView : PreviewView,
        lifecycleOwner : LifecycleOwner,
    )
}