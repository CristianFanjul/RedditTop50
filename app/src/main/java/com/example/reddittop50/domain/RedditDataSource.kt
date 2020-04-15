package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams
import com.example.reddittop50.retrofit.IApiClient
import javax.inject.Inject

interface IRedditDataSource {
    fun requestArticles(queryParams: QueryParams): Result<ApiResponse>
}

class RedditRemoteDataSource @Inject constructor(
    private val redditClient: IApiClient
) : IRedditDataSource {
    override fun requestArticles(queryParams: QueryParams): Result<ApiResponse> =
        callAndResult({
            redditClient.requestArticles(queryParams.limit, queryParams.after)
        }, {
            ApiResponse(null)
        })
}