package com.example.reddittop50.ui.home

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reddittop50.R
import com.example.reddittop50.misc.ImageLoader
import com.example.reddittop50.model.Article
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            txt_vw_home.text = it
        })
    }

    fun updateFragment(item: Article) {
        txt_vw_home.isGone = true

        val views = arrayOf(
            txt_vw_article_user_detail,
            txt_vw_article_title_detail,
            img_vw_article_image_detail
        )
        // Make views visible
        views.forEach { it.isVisible = true }

        txt_vw_article_user_detail.text = item.author ?: ""
        txt_vw_article_title_detail.text = item.title ?: ""
        item.thumbnail?.let { ImageLoader.loadImage(it, img_vw_article_image_detail) }

    }
}