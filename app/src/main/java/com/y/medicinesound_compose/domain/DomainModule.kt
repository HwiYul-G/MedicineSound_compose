package com.y.medicinesound_compose.domain

import com.y.medicinesound_compose.domain.repositories.TextToSpeechRepo
import com.y.medicinesound_compose.domain.usecases.TextToSpeechUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    fun provideTextToSpeechUseCase(textToSpeechRepo : TextToSpeechRepo) : TextToSpeechUseCase{
        return TextToSpeechUseCase(textToSpeechRepo)
    }
}