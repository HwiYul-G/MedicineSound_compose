package com.y.medicinesound_compose.data

import android.content.Context
import com.y.medicinesound_compose.data.local.datasource.TextToSpeechDataSource
import com.y.medicinesound_compose.data.local.datasourceImpl.TextToSpeechDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideTextToSpeech(context : Context) : TextToSpeechDataSource {
        return TextToSpeechDataSourceImpl(context)
    }
}