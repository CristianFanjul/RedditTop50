package com.example.reddittop50.model

data class ApiResponse(val data: Data?)

data class Data(val articles: List<DataChildren>?, val after: String?, val before: String?)

data class DataChildren(val article: Article?)