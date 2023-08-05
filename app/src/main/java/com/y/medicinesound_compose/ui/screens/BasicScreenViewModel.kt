package com.y.medicinesound_compose.ui.screens


import android.graphics.Bitmap
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.y.medicinesound_compose.domain.usecases.EYak.GetEYakListUseCase
import com.y.medicinesound_compose.domain.usecases.GetObjDetectionResult
import com.y.medicinesound_compose.model.BasicUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BasicScreenViewModel @Inject constructor(
    private val getEYakListUseCase: GetEYakListUseCase,
    private val getObjDetectionResult: GetObjDetectionResult,
) : ViewModel() {
    private val TAG = "BasicScreenViewModel"

    private val _uiState = MutableStateFlow(BasicUiState())
    val uiState = _uiState.asStateFlow()



    fun updateUiStateImageUri(imageUri: String) {
        _uiState.update { currentState ->
            currentState.copy(
                medicineImg = imageUri
            )
        }
    }

    private fun updateUiStateMedicineName(medicineName: String) {
        _uiState.update { currentState ->
            currentState.copy(
                medicineName = medicineName
            )
        }
    }

    fun getEYakEffect(itemName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "getEYakEffect called with itemName: $itemName")
                val result = getEYakListUseCase(itemName)
                if (result.isSuccess) {
                     Log.d(TAG, "getEYakEffect result: ${result.getOrNull()}")
                    withContext(Dispatchers.Main) {
                        val list = result.getOrNull()
                        list?.let {
                            updateUiStateMedicineEffect(it[0].efcyQesitm)
                        }
                    }
                } else {
                    Log.d(TAG, "getEYakEffect failed: ${result.exceptionOrNull()}")
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

    // TODO : uiState의 medicineName이 updated 안되는 문제.
    fun getObjDetectionResult(
        bitmap: Bitmap,
        imageViewWidth: Float,
        imageViewHeight: Float,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result =
                    getObjDetectionResult.invoke(bitmap, imageViewWidth, imageViewHeight)
                Log.d(TAG, "getObjDetectionResult: ${result[0].classIndex}")
                val medicineName = getMedicineNameFromIndex(result[0].classIndex)
                Log.d(TAG, "getObjDetectionResult: $medicineName")
                withContext(Dispatchers.Main) {
                    updateUiStateMedicineName(medicineName)
                }
            } catch (e: java.lang.Exception) {
                Log.e(TAG, "getObjDetectionResult: ${e.message}")
            }

        }
    }

    private fun getMedicineNameFromIndex(index: Int): String {
        return when (index) {
            0 -> "판콜에이내복액"
            1 -> "타이레놀"
            2 -> "닥터베아제정"
            3 -> "훼스탈플러스정"
            4 -> "판피린티정"
            5 -> "신신파스아렉스"
            6 -> "베아제정"
            else -> "편의점 상비약 7종만 인삭합니다."
        }
    }


}