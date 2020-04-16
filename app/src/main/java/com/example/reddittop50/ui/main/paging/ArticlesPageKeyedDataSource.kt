package com.example.reddittop50.ui.main.paging

import androidx.paging.PageKeyedDataSource
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesPageKeyedDataSource(
    private val articlesUseCase: GetArticlesUseCase,
    val queryParams: QueryParams,
    private val pagedCallback: ArticlePagedCallback
) :
    PageKeyedDataSource<String, Article>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Article>
    ) {
        loadArticles(null) { articles, after ->
            callback.onResult(articles, null, after)
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        loadArticles(params.key) { articles, after ->
            callback.onResult(articles, after)
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Article>) {
        // Not used. Adapter starts form page 0 and only adds items.
    }

    private fun loadArticles(
        key: String?,
        success: (articles: List<Article>, after: String?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val queryParams = QueryParams(after = key)
            val apiResponse = articlesUseCase.invoke(queryParams)
            if (apiResponse is Result.Success) {
                pagedCallback.onSuccess()
                val data = apiResponse.content.data
                val after = data?.after
                val articlesList = mutableListOf<Article>()
                data?.children?.map {
                    it.article?.let { article ->
                        articlesList.add(article)
                    }
                }

                success(articlesList, after)
            } else {
                val message = (apiResponse as Result.Error).exception.message
                pagedCallback.onError(message ?: "Generic message.")
            }
        }
    }
}


interface ArticlePagedCallback {
    fun onSuccess()
    fun onError(message: String)
}