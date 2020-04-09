package com.example.reddittop50.ui.main.paging

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittop50.R
import com.example.reddittop50.RedditTop50App
import com.example.reddittop50.misc.ImageLoader
import com.example.reddittop50.model.Article
import kotlinx.android.synthetic.main.recycler_item_article.view.*
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


class ArticleViewHolder(
    itemView: View,
    private val listener: ArticlesPagedAdapter.IOnArticleListener
) : RecyclerView.ViewHolder(itemView) {

    fun setValues(item: Article) {
        // TODO: building and object with the data to display in the view model would be better and more performant.

        if (item.visible) {
            itemView.vw_article_item_root.layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        } else {
            itemView.vw_article_item_root.layoutParams = ConstraintLayout.LayoutParams(1, 1)
        }

        itemView.txt_vw_username.text = item.author
        val timeAgo = getTimeAgo(item)
        itemView.txt_vw_time.text = timeAgo
        itemView.txt_vw_comments.text =
            RedditTop50App.instance.getString(R.string.comments_number, item.comments ?: 0)
        itemView.txt_vw_article_title.text = item.title ?: ""
        itemView.img_vw_read_bubble.isVisible = !item.read

        itemView.vw_article_item_root.setOnClickListener { listener.onArticleClicked(item) }
        itemView.txt_vw_dismiss_post.setOnClickListener { listener.onArticleDismissed(item) }
        itemView.img_vw_dismiss_post.setOnClickListener { listener.onArticleDismissed(item) }

        item.thumbnail?.let { ImageLoader.loadImage(it, itemView.img_vw_article_image) }
    }

    private fun getTimeAgo(item: Article): CharSequence {
        val prettyTime = PrettyTime(Locale.getDefault())
        val unixToMills = (item.created_utc ?: 0) * 1000
        return prettyTime.format(Date(unixToMills))
    }
}