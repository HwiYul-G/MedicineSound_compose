package com.y.medicinesound_compose.domain.repositories

import android.content.Context
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.Flow

interface CustomCameraRepo {

    suspend fun captureAndSaveImage(context : Context)
    suspend fun showCameraPreview(
        previewView : PreviewView,
        lifecycleOwner : LifecycleOwner,
    )

    fun getSavedImageUri() : Flow<Uri?>
}