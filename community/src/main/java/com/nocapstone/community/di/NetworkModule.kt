package com.nocapstone.community.di

import com.nocapstone.community.data.CommunityService
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
    fun provideDiaryService(retrofit: Retrofit): CommunityService {
        return retrofit.create(CommunityService::class.java)
    }

}