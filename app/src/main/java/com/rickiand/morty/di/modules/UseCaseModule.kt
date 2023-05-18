package com.rickiand.morty.di.modules

import com.rickiand.morty.domain.usecase.MainUseCase
import com.rickiand.morty.domain.usecase.MainUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun providerMainUseCase(mainUseCaseImpl: MainUseCaseImpl): MainUseCase
}