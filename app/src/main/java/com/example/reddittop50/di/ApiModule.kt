package com.example.reddittop50.di

import com.example.reddittop50.retrofit.IApiClient
import com.example.reddittop50.retrofit.RetrofitFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Singleton
    @Provides
    fun provideApiClient(): IApiClient = RetrofitFactory.articlesRetrofitService()
}