package com.y.medicinesound_compose.domain.repositories

import com.y.medicinesound_compose.data.local.TextToSpeechResult

interface TextToSpeechRepo {
    suspend fun speak(text : String) : TextToSpeechResult
}