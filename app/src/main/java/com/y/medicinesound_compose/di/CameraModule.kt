package com.y.medicinesound_compose.di

import android.app.Application
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import com.y.medicinesound_compose.data.repositoryImpls.CustomCameraRepoImpl
import com.y.medicinesound_compose.domain.repositories.CustomCameraRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CameraModule {

    @Provides
    @Singleton
    fun provideCameraSelector(): CameraSelector {
        // select the camera front or back
        return CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    @Provides
    @Singleton
    fun provideCameraProvider(application: Application): ProcessCameraProvider {
        // provide camera instance
        return ProcessCameraProvider.getInstance(application).get()
    }

    @Provides
    @Singleton
    fun provideCameraPreview() : Preview{
        // for previewing whatever behind the camera
        return Preview.Builder().build()
    }

    @Provides
    @Singleton
    fun provideImageCapture() : ImageCapture{
        // for capturing image
        return ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .build()
    }

    @Provides
    @Singleton
    fun provideResolutionSelector() : ResolutionSelector{
        return ResolutionSelector.Builder()
            .setAspectRatioStrategy(AspectRatioStrategy.RATIO_16_9_FALLBACK_AUTO_STRATEGY)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageAnalysis() : ImageAnalysis{
        return ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    @Provides
    @Singleton
    fun provideCustomCameraRepo(
        cameraProvider: ProcessCameraProvider,
        selector: CameraSelector,
        imageCapture: ImageCapture,
        preview: Preview,
        resolutionSelector: ResolutionSelector,
        imageAnalysis: ImageAnalysis,
    ) : CustomCameraRepo{
        return CustomCameraRepoImpl(
            cameraProvider,
            selector,
            preview,
            imageCapture,
            resolutionSelector,
            imageAnalysis,
        )
    }


}