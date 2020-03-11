package com.example.reddittop50.domain

import android.content.Context
import com.example.reddittop50.retrofit.RetrofitFactory

object ServiceLocator {

    var redditRepository: RedditRepository? = null

    fun provideRedditRepository(context: Context): RedditRepository {
        synchronized(this) {
            return redditRepository ?: redditRepository ?: createRedditRepository(context)
        }
    }

    private fun createRedditRepository(context: Context): RedditRepository {
        val redditDataSource = RedditRemoteDataSource(RetrofitFactory.articlesRetrofitService())
        return RedditRepository(redditDataSource)
    }
}