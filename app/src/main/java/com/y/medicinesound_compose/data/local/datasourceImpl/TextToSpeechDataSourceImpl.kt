package com.y.medicinesound_compose.data.local.datasourceImpl

import android.content.Context
import android.speech.tts.TextToSpeech
import com.y.medicinesound_compose.data.local.datasource.TextToSpeechDataSource
import javax.inject.Inject

class TextToSpeechDataSourceImpl @Inject constructor(private val context: Context) : TextToSpeechDataSource {
    private var textToSpeech: TextToSpeech? = null

    init {
        textToSpeech = TextToSpeech(context){status ->
            if(status == TextToSpeech.SUCCESS){
                // TTS 초기화 성공
            }
        }
    }

    override fun speak(text : String, callback : (Boolean) -> Unit){
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        // TTS 작업이 완료된 후에 콜백을 호출해 상태를 알린다.
        // 여기선 간단하게 true로 처리한다.
        callback(true)
    }

    fun release(){
        // 반드시 tts 리소스를 해제해야 한다.
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }

}