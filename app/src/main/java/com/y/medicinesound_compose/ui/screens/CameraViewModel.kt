package com.y.medicinesound_compose.ui.screens

import android.content.Context
import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.medicinesound_compose.domain.usecases.CaptureAndSaveImageUseCase
import com.y.medicinesound_compose.domain.usecases.GetCameraPreviewUseCase
import com.y.medicinesound_compose.domain.usecases.GetUrlCameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getUriCameraUseCase: GetUrlCameraUseCase,
    private val showCameraPreviewUseCase: GetCameraPreviewUseCase,
    private val captureAndSaveImageUseCase: CaptureAndSaveImageUseCase,
) : ViewModel() {

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri : StateFlow<Uri?> = _imageUri


    init{
        viewModelScope.launch {
            getUriCameraUseCase().collect{
                _imageUri.value = it
            }
        }
    }

    fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner,
    ) {
        viewModelScope.launch {
            showCameraPreviewUseCase.showCameraPreview(previewView, lifecycleOwner)
        }
    }

    fun captureAndSave(context : Context){
        viewModelScope.launch {
            captureAndSaveImageUseCase.captureAndSaveImage(context)
        }
    }


}