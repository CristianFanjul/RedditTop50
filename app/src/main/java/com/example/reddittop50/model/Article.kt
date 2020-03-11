package com.example.reddittop50.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Article(
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    val date: Long,
    val thumbnail: String,
    val comments: Int
) : Parcelable