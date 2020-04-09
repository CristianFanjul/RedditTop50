package com.example.reddittop50.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Article(
    val id: String,
    val title: String?,
    val author: String?,
    val created_utc: Long?,
    val thumbnail: String?,
    @SerializedName("num_comments") val comments: Int = 0,
    var read: Boolean = false // TODO: this could be handled in other class for visual elements.
    , var visible: Boolean = true
) : Parcelable