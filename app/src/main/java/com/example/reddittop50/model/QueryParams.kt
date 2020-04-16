package com.example.reddittop50.model

data class QueryParams(val limit: Int = 25, var after: String? = null)