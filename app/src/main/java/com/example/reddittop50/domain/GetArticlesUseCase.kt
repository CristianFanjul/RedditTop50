package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val redditRepository: IRedditRepository
) : UseCase<ApiResponse, QueryParams>() {
    override suspend fun invoke(params: QueryParams): Result<ApiResponse> {
        return try {
            redditRepository.requestArticles(params)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}