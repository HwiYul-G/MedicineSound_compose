package com.y.medicinesound_compose.di

import android.app.Application
import android.content.Context
import com.y.medicinesound_compose.data.local.datasource.TextToSpeechDataSource
import com.y.medicinesound_compose.data.local.datasourceImpl.TextToSpeechDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext(application: Application) : Context {
        return application.applicationContext
    }



}