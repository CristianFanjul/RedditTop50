package com.example.reddittop50.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reddittop50.RedditTop50App
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<Article>>().apply { value = mutableListOf() }
    val items: LiveData<MutableList<Article>> = _items

    private val _loadMoreItems =
        MutableLiveData<MutableList<Article>>().apply { value = mutableListOf() }
    val loadMoreItems: LiveData<MutableList<Article>> = _loadMoreItems

    var queryParams = QueryParams()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    @Inject
    lateinit var articlesUseCase: GetArticlesUseCase

    init {
        RedditTop50App.instance.androidInjector().inject(this)
    }

    fun loadInitialArticles() {
        _dataLoading.value = true
        requestArticles {
            _items.postValue(it)
        }
    }

    fun refreshArticles() {
        queryParams.after = null
        requestArticles {
            _items.postValue(it)
        }
    }

    fun loadMoreArticles() {
        requestArticles {
            _loadMoreItems.postValue(it)
        }
    }

    private fun requestArticles(onSuccess: (MutableList<Article>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse = articlesUseCase.invoke(queryParams)
            if (apiResponse is Result.Success) {
                val data = apiResponse.content.data
                queryParams.after = data?.after ?: queryParams.after
                val articlesList = mutableListOf<Article>()
                // TODO: put this logic in the use case.
                data?.children?.map {
                    it.article?.let { article ->
                        articlesList.add(article)
                    }
                }
                _dataLoading.postValue(false)
                onSuccess(articlesList)
            } else {
                _dataLoading.postValue(false)
                val message = (apiResponse as Result.Error).exception.message
                _snackbarText.postValue(message)
            }
        }
    }
}