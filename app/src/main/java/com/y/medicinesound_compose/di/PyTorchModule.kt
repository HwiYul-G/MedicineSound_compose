package com.y.medicinesound_compose.di


import android.content.Context
import android.util.Log
import com.y.medicinesound_compose.utils.AssetFilePathProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.pytorch.LiteModuleLoader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PyTorchModule {

    @Provides
    @Singleton
    fun provideAssetFilePathProvider(@ApplicationContext context : Context) : AssetFilePathProvider{
        return AssetFilePathProvider(context)
    }

    @Provides
    @Singleton
    fun providePyTorchModule(assetFilePathProvider: AssetFilePathProvider) : org.pytorch.Module{
        val filePath = assetFilePathProvider.assetFilePath("best.ptl")
        Log.d("PyTorchModule", "filePath : $filePath")
        return LiteModuleLoader.load(filePath)
    }
}