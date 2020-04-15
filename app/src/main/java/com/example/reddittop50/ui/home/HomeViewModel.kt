package com.example.reddittop50.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.reddittop50.RedditTop50App
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams
import com.example.reddittop50.ui.main.paging.ArticlePagedCallback
import com.example.reddittop50.ui.main.paging.ArticlesDataFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    lateinit var items: LiveData<PagedList<Article>>
    var queryParams = QueryParams()
    val dataLoading = MutableLiveData<Boolean>()
    val snackbarText = MutableLiveData<String>()

    @Inject
    lateinit var articlesUseCase: GetArticlesUseCase

    init {
        RedditTop50App.instance.androidInjector().inject(this)
    }

    private val pagedCallback = object :
        ArticlePagedCallback {
        override fun onSuccess() {
            dataLoading.postValue(false)
        }

        override fun onError(message: String) {
            postShowSnackbarMessage("Loading articles error: $message}")
            dataLoading.postValue(false)
        }
    }

    init {
        loadInitialArticles()
    }

    private fun loadInitialArticles() {
        dataLoading.value = true
        requestArticles()
    }


    protected suspend fun showSnackbarMessage(message: String) {
        withContext(Dispatchers.Main) {
            snackbarText.value = message
        }
    }

    protected fun postShowSnackbarMessage(message: String) {
        snackbarText.postValue(message)
    }

    fun refreshArticles() {
        requestArticles()
    }

    private fun requestArticles() {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(25).build()

        items = LivePagedListBuilder(
            ArticlesDataFactory(articlesUseCase, queryParams, pagedCallback),
            pagedListConfig
        ).build()
    }
}