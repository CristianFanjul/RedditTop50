package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams
import com.example.reddittop50.retrofit.IApiClient
import javax.inject.Inject

interface IRedditDataSource {
    suspend fun requestArticles(queryParams: QueryParams): Result<ApiResponse>
}

class RedditRemoteDataSource @Inject constructor(
    private val redditClient: IApiClient
) : IRedditDataSource {

    override suspend fun requestArticles(queryParams: QueryParams): Result<ApiResponse> {
        return Result.Success(redditClient.requestArticles(queryParams.limit, queryParams.after))
    }
}