package com.example.reddittop50

import android.accounts.NetworkErrorException
import com.example.reddittop50.domain.IRedditRepository
import com.example.reddittop50.domain.Result
import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.QueryParams

class UnitTestsRepository : IRedditRepository {
    var error = false

    override suspend fun requestArticles(queryParams: QueryParams): Result<ApiResponse> {
        return if (error) {
            val exception = NetworkErrorException("Error: error test")
            Result.Error(exception)
        } else {
            Result.Success(SampleDataProvider.getArticlesSample())
        }
    }

}