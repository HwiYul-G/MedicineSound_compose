package com.y.medicinesound_compose.data.local.datasource

interface TextToSpeechDataSource {
    fun speak(text : String, callback : (Boolean) -> Unit)
}