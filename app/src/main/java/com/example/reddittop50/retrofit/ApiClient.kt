package com.example.reddittop50.retrofit

import com.example.reddittop50.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiClient {
    @GET("top.json")
    fun requestArticles(
        @Query("limit") limit: Int = 25,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Call<ApiResponse>
}