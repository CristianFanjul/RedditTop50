package com.example.reddittop50

import android.app.Application
import com.example.reddittop50.domain.RedditRepository
import com.example.reddittop50.domain.ServiceLocator

class RedditTop50App : Application() {

    companion object {
        lateinit var instance: RedditTop50App
    }

    val redditRepository: RedditRepository
        get() = ServiceLocator.provideRedditRepository(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}