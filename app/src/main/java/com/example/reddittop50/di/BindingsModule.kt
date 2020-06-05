package com.example.reddittop50.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reddittop50.domain.IRedditDataSource
import com.example.reddittop50.domain.IRedditRepository
import com.example.reddittop50.domain.RedditRemoteDataSource
import com.example.reddittop50.domain.RedditRepository
import com.example.reddittop50.ui.detail.DetailViewModel
import com.example.reddittop50.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface BindingsModule {
    @Binds
    fun bindRedditRepository(repository: RedditRepository): IRedditRepository

    @Binds
    fun bindRedditDataSource(dataSource: RedditRemoteDataSource): IRedditDataSource

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindsHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    fun bindsDetailViewModel(detailViewModel: DetailViewModel): ViewModel
}