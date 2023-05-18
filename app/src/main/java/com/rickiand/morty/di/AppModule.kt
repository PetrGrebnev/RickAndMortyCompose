package com.rickiand.morty.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    lateinit var context: Context

    @Provides
    @ApplicationContext
    fun provideContext() : Context = context

    @Provides
    fun providerApp() : Application = Contexts.getApplication(context)
}