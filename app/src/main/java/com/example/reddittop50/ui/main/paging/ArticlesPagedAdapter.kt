package com.example.reddittop50.ui.main.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.reddittop50.R
import com.example.reddittop50.model.Article
import com.example.reddittop50.ui.main.IOnArticleListener

class ArticlesPagedAdapter(private val listener: IOnArticleListener) :
    PagedListAdapter<Article, ArticleViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_article, parent, false)
        return ArticleViewHolder(
            view,
            listener
        )
    }

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, position: Int) {
        val vm = getItem(position)
        vm?.let { viewHolder.setValues(it) }
    }

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Article> =
            object : DiffUtil.ItemCallback<Article>() {
                override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                    oldItem == newItem
            }
    }
}