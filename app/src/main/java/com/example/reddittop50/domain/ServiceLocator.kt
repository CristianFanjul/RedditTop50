package com.example.reddittop50.domain

import com.example.reddittop50.retrofit.RetrofitFactory

object ServiceLocator {

    var redditRepository: IRedditRepository? = null

    fun provideRedditRepository(): IRedditRepository {
        synchronized(this) {
            return redditRepository ?: redditRepository ?: createRedditRepository()
        }
    }

    private fun createRedditRepository(): IRedditRepository {
        val redditDataSource = RedditRemoteDataSource(RetrofitFactory.articlesRetrofitService())
        return RedditRepository(redditDataSource)
    }
}