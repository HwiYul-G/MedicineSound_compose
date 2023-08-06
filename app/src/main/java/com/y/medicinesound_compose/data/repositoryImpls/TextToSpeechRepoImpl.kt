package com.y.medicinesound_compose.data.repositoryImpls

import com.y.medicinesound_compose.data.local.TextToSpeechResult
import com.y.medicinesound_compose.data.local.datasource.TextToSpeechDataSource
import com.y.medicinesound_compose.domain.repositories.TextToSpeechRepo
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TextToSpeechRepoImpl @Inject constructor(private val textToSpeechDataSource : TextToSpeechDataSource) : TextToSpeechRepo{

    override suspend fun speak(text : String) : TextToSpeechResult{
        return withContext(Dispatchers.IO){
            val result = CompletableDeferred<TextToSpeechResult>()

            textToSpeechDataSource.speak(text){success->
                val textToSpeechResult = TextToSpeechResult(success)
                result.complete(textToSpeechResult)
            }

            result.await()
        }
    }

}