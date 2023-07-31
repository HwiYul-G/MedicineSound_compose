package com.y.medicinesound_compose.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import com.y.medicinesound_compose.model.BasicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BasicScreenViewModel @Inject constructor(
//    private val eYakService: EYakService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BasicUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUiStateImageUri(imageUri : String){
        _uiState.update { currentState->
            currentState.copy(
                medicineImg = imageUri
            )
        }
    }



}