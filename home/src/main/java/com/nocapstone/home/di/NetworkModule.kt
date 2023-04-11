package com.nocapstone.home.di

import com.nocapstone.buddyvet.buddy.data.BuddyService
import com.nocapstone.home.data.KaKaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideKakaoService(): KaKaoService {
        val retrofit = Retrofit.Builder() // Retrofit 구성
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(KaKaoService::class.java)
    }
}