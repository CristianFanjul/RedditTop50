package com.example.reddittop50.di

import com.example.reddittop50.ui.home.HomeViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributorBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainViewModel(): HomeViewModel
}