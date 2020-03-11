package com.example.reddittop50.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.RedditRepository
import com.example.reddittop50.ui.main.MainViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val redditRepository: RedditRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(
                        GetArticlesUseCase(redditRepository)
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}