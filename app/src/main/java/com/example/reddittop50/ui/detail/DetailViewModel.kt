package com.example.reddittop50.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Select a post to read the details."
    }
    val text: LiveData<String> = _text
}