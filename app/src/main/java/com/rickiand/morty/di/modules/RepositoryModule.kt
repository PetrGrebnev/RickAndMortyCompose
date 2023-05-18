package com.rickiand.morty.di.modules

import com.rickiand.morty.data.repository.MainRepository
import com.rickiand.morty.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providerMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository
}