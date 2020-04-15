package com.example.reddittop50

import com.example.reddittop50.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RedditTop50App : DaggerApplication() {

    companion object {
        lateinit var instance: RedditTop50App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this)!!.build()!!
    }
}