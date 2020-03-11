package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.Article
import com.example.reddittop50.model.Data
import com.example.reddittop50.model.QueryParams
import com.example.reddittop50.retrofit.IApiClient

interface IRedditDataSource {
    fun requestArticles(queryParams: QueryParams): Result<ApiResponse>
}

class RedditRemoteDataSource(private val redditClient: IApiClient) : IRedditDataSource {
    override fun requestArticles(queryParams: QueryParams): Result<ApiResponse> =
        callAndResult({
            redditClient.requestArticles(queryParams.limit, queryParams.after)
        }, {
            ApiResponse(null)
        })
}