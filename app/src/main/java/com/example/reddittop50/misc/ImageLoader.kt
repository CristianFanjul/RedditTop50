package com.example.reddittop50.misc

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.reddittop50.RedditTop50App

object ImageLoader {
    fun loadImage(thumbnail: String, view: ImageView) {
        Glide.with(RedditTop50App.instance.applicationContext)
            .load(thumbnail)
            .centerCrop()
            .into(view)
    }
}
