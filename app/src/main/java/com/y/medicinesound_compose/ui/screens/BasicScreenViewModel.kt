package com.y.medicinesound_compose.ui.screens

import androidx.lifecycle.ViewModel
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import com.y.medicinesound_compose.model.BasicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BasicScreenViewModel @Inject constructor(
//    private val eYakService: EYakService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BasicUiState())
    val uiState = _uiState.asStateFlow()



}