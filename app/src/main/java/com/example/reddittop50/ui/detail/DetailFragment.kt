package com.example.reddittop50.ui.detail

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.misc.ImageLoader
import com.example.reddittop50.model.Article
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        fun newInstance(article: Article) = DetailFragment().also {
            val bundle = Bundle()
            bundle.putParcelable(Constants.ARTICLE_KEY, article)
            it.arguments = bundle
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        val article: Article = arguments?.getParcelable(Constants.ARTICLE_KEY)
            ?: throw RuntimeException("Article not present.")

        updateFragment(article)
    }

    private fun updateFragment(item: Article) {
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
