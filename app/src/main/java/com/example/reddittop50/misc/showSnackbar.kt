package com.example.reddittop50.misc

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View?.showSnackbar(snackbarText: String, timeLength: Int = Snackbar.LENGTH_LONG) {
    this?.let { Snackbar.make(it, snackbarText, timeLength).show() }
}