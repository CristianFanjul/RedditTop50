package com.example.reddittop50.ui.main.paging

import androidx.paging.DataSource
import com.example.reddittop50.domain.GetArticlesUseCase
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams

class ArticlesDataFactory(
    private val articlesUseCase: GetArticlesUseCase,
    private val params: QueryParams,
    private val pagedCallback: ArticlePagedCallback
) :
    DataSource.Factory<String, Article>() {
    override fun create(): DataSource<String, Article> {
        return ArticlesPageKeyedDataSource(articlesUseCase, params, pagedCallback)
    }
}