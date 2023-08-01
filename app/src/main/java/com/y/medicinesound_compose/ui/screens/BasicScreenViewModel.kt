package com.y.medicinesound_compose.ui.screens


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.medicinesound_compose.domain.usecases.EYak.GetEYakListUseCase
import com.y.medicinesound_compose.model.BasicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicScreenViewModel @Inject constructor(
    private val getEYakListUseCase: GetEYakListUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BasicUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUiStateImageUri(imageUri: String) {
        _uiState.update { currentState ->
            currentState.copy(
                medicineImg = imageUri
            )
        }
    }

    fun getEYakEffect(itemName: String) {
        viewModelScope.launch {
            try {
                // Log.d("Debug", "getEYakEffect called with itemName: $itemName")
                val result = getEYakListUseCase(itemName)
                if (result.isSuccess) {
                    // Log.d("Debug", "getEYakEffect result: ${result.getOrNull()}")
                    val list = result.getOrNull()
                    list?.let {
                        updateUiStateMedicineEffect(it[0].efcyQesitm)
                    }
                } else {
                    // Log.d("Debug", "getEYakEffect failed: ${result.exceptionOrNull()}")
                }
            } catch (e: Exception) {
                Log.e("Debug", "Exception occurred: ${e.message}")
            }
        }
    }

    private fun updateUiStateMedicineEffect(medicineEffect: String) {
        Log.d("Debug", "updateUiStateMedicineEffect called with medicineEffect: ${medicineEffect}")
        _uiState.update { currentState ->
            currentState.copy(
                medicineEffect = medicineEffect
            )
        }
    }


}