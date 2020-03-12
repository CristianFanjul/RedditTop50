package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams

interface IRedditRepository {
    fun requestArticles(queryParams: QueryParams): Result<ApiResponse>
}

class RedditRepository(private val remoteDataSource: RedditRemoteDataSource) : IRedditRepository {
    override fun requestArticles(queryParams: QueryParams): Result<ApiResponse> =
        remoteDataSource.requestArticles(queryParams)
}