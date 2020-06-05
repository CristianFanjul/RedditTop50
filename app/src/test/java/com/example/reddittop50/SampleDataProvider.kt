package com.example.reddittop50

import com.example.reddittop50.model.ApiResponse
import com.example.reddittop50.model.Data
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File

object SampleDataProvider {
    fun getArticlesSample(): ApiResponse {
        val filePath = "src\\test\\resources\\com\\example\\reddittop50\\sample.json"
        val bufferedReader: BufferedReader = File(filePath).bufferedReader()

        // Read the text from buffferReader and store in String variable
        val inputString = bufferedReader.use { it.readText() }

        //Convert the Json File to Gson Object
        return Gson().fromJson(inputString, ApiResponse::class.java)
    }

    fun getArticlesWithLimit(limit: Int): ApiResponse {
        val response = getArticlesSample()
        val sublist = response.data?.children?.subList(0, limit)
        val newData = Data(sublist, null, null)
        return ApiResponse(newData)
    }
}