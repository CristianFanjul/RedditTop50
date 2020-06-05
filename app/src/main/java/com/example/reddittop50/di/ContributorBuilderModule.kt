package com.example.reddittop50.di

import com.example.reddittop50.ui.detail.DetailFragment
import com.example.reddittop50.ui.detail.DetailViewModel
import com.example.reddittop50.ui.home.HomeFragment
import com.example.reddittop50.ui.home.HomeViewModel
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContributorBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeMainViewModel(): HomeViewModel

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailViewModel(): DetailViewModel

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment
}