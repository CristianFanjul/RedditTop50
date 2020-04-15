package com.example.reddittop50.di

import com.example.reddittop50.domain.*
import dagger.Binds
import dagger.Module

@Module
interface BindingsModule {
    @Binds
    fun bindRedditRepository(repository: RedditRepository): IRedditRepository

    @Binds
    fun bindRedditDataSource(dataSource: RedditRemoteDataSource): IRedditDataSource
}