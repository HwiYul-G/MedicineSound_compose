package com.y.medicinesound_compose.di


import com.y.medicinesound_compose.data.repositoryImpls.EYakRepoImpl
import com.y.medicinesound_compose.data.repositoryImpls.YOLORepoImpl
import com.y.medicinesound_compose.domain.repositories.EYakRepo
import com.y.medicinesound_compose.domain.repositories.YOLORepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEYakRepo(eYakRepoImpl: EYakRepoImpl): EYakRepo

    @Binds
    abstract fun bindYOLORepo(yoloRepoImpl: YOLORepoImpl): YOLORepo


}