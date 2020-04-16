package com.example.reddittop50.misc

import android.content.res.Configuration
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.example.reddittop50.RedditTop50App
import com.google.android.material.snackbar.Snackbar

fun FragmentActivity?.isScreenLandscape(): Boolean {
    return RedditTop50App.instance.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun Int.isLandscape(): Boolean {
    return this == Configuration.ORIENTATION_LANDSCAPE
}

fun View?.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_SHORT) {
    this?.let { Snackbar.make(it, snackbarText, timeLength).show() }
}