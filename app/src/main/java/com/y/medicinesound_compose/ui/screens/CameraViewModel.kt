package com.y.medicinesound_compose.ui.screens

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val customCameraRepo: CustomCameraRepo,
) : ViewModel() {

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
    ) {
        viewModelScope.launch {
            customCameraRepo.showCameraPreview(previewView, lifecycleOwner)
        }
    }

    fun captureAndSave(context : Context){
        viewModelScope.launch {
            customCameraRepo.captureAndSaveImage(context)
        }
    }

}