package com.nocapstone.buddyvet

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class MainModule {

    @Singleton
    @Provides
    fun provideMainActivityClass(): Class<*> {
        return MainActivity::class.java
    }

}