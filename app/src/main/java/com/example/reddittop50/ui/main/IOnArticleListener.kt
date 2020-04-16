package com.example.reddittop50.ui.main

import com.example.reddittop50.model.Article

interface IOnArticleListener {
    fun onArticleClicked(item: Article)
    fun onArticleDismissed(item: Article)
}