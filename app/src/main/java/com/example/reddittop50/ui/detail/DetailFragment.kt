package com.example.reddittop50.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.model.Article

class DetailFragment : Fragment(R.layout.fragment_detail) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val article = arguments?.getParcelable<Article>(Constants.ARTICLE_KEY)
            ?: throw RuntimeException("Article key not present.")
    }
}
