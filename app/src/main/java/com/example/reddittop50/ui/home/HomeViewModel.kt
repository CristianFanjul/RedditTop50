package com.example.reddittop50.ui.home

import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val articlesUseCase: GetArticlesUseCase
) : ViewModel() {
    private val _items = MutableLiveData<MutableList<Article>>()
    val items: LiveData<MutableList<Article>> = _items

    private val _loadMoreItems = MutableLiveData<MutableList<Article>>()
    val loadMoreItems: LiveData<MutableList<Article>> = _loadMoreItems

    var initialParams = QueryParams()
    var loadMoreParams = QueryParams()

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    fun loadInitialArticles() {
        _dataLoading.value = true
        requestArticles(initialParams) {
            _items.postValue(it)
        }
    }

    fun refreshArticles() {
        requestArticles(initialParams) {
            _items.postValue(it)
        }
    }

    fun loadMoreArticles() {
        requestArticles(loadMoreParams) {
            _loadMoreItems.postValue(it)
        }
    }

    private fun requestArticles(params: QueryParams, onSuccess: (MutableList<Article>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val apiResponse = articlesUseCase.invoke(params)
            if (apiResponse is Result.Success) {
                val data = apiResponse.content.data
                loadMoreParams.after = data?.after ?: loadMoreParams.after
                val articlesList = mutableListOf<Article>()
                // TODO: put this logic in the use case.
                data?.children?.map {
                    it.article?.let { article ->
                        articlesList.add(article)
                    }
                }
                val mainThread = Looper.myLooper() == Looper.getMainLooper()
                Log.d(this.javaClass.name, " Main Thread: $mainThread")

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