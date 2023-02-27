package com.nocapstone.diary.di

import com.nocapstone.diary.data.DiaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideDiaryService(retrofit: Retrofit): DiaryService {
        return retrofit.create(DiaryService::class.java)
    }

}