package com.example.reddittop50.di

import android.app.Application
import com.example.reddittop50.RedditTop50App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AndroidSupportInjectionModule::class,
        BindingsModule::class,
        ContributorBuilderModule::class
    ]
)
interface AppComponent : AndroidInjector<RedditTop50App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?

        fun build(): AppComponent?
    }
}