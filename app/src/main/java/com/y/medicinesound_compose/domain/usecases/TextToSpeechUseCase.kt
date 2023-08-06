package com.y.medicinesound_compose.domain.usecases

import com.y.medicinesound_compose.domain.repositories.TextToSpeechRepo
import javax.inject.Inject

class TextToSpeechUseCase @Inject constructor(private val textToSpeechRepo: TextToSpeechRepo) {
    suspend operator fun invoke(text: String) = textToSpeechRepo.speak(text)
}