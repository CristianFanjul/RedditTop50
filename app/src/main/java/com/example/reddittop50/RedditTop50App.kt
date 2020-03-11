package com.example.reddittop50

import android.app.Application

class RedditTop50App : Application() {

    companion object {
        lateinit var instance: RedditTop50App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}