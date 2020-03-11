package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.QueryParams

class GetArticlesUseCase(private val redditRepository: RedditRepository) {
    suspend operator fun invoke(queryParams: QueryParams): Result<ApiResponse> {
        return redditRepository.requestArticles(queryParams)
    }
}