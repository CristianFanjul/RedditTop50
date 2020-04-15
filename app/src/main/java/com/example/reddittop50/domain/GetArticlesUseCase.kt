package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val redditRepository: IRedditRepository
) {
    operator fun invoke(queryParams: QueryParams): Result<ApiResponse> {
        return redditRepository.requestArticles(queryParams)
    }
}
