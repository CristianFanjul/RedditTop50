package com.example.reddittop50.domain

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams
import javax.inject.Inject

interface IRedditRepository {
    fun requestArticles(queryParams: QueryParams): Result<ApiResponse>
}

class RedditRepository @Inject constructor(
    private val remoteDataSource: IRedditDataSource
) : IRedditRepository {
    override fun requestArticles(queryParams: QueryParams): Result<ApiResponse> =
        remoteDataSource.requestArticles(queryParams)
}