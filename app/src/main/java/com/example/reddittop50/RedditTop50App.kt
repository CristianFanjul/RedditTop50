package com.example.reddittop50

import android.app.Application
import com.example.reddittop50.domain.IRedditRepository
import com.example.reddittop50.domain.ServiceLocator

class RedditTop50App : Application() {

    companion object {
        lateinit var instance: RedditTop50App
    }

    val redditRepository: IRedditRepository get() = ServiceLocator.provideRedditRepository()

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}