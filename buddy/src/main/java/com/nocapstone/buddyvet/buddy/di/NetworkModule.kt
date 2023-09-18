package com.nocapstone.buddyvet.buddy.di

import com.nocapstone.buddyvet.buddy.data.BuddyService
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
    fun provideBuddyService(retrofit: Retrofit): BuddyService {
        return retrofit.create(BuddyService::class.java)
    }


}