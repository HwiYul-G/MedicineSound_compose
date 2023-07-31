package com.y.medicinesound_compose.di



import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // CustomCameraRepo는 CameraModule을 사용하자!
//    @Binds
//    @Singleton
//    abstract fun bindCustomCameraRepo(
//        customCameraRepoImpl: CustomCameraRepoImpl
//    ) : CustomCameraRepo


}