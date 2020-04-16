package com.example.reddittop50.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.misc.isScreenLandscape
import com.example.reddittop50.misc.showSnackbar
import com.example.reddittop50.model.Article
import com.example.reddittop50.ui.ViewModelFactory
import com.example.reddittop50.ui.detail.DetailFragment
import com.example.reddittop50.ui.main.ArticlesAdapter
import com.example.reddittop50.ui.main.IOnArticleListener
import com.example.reddittop50.ui.main.MainActivity
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), IOnArticleListener {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory()
    }

    private val adapter = ArticlesAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar()
        setupAdapter()
        setupObservers()

        viewModel.loadInitialArticles()
    }


    private fun setupToolbar() {
        setHasOptionsMenu(true)
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        vw_recycler.layoutManager = layoutManager
        vw_recycler.adapter = adapter
        vw_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isRecyclerBottom(dy, layoutManager)) viewModel.loadMoreArticles()
            }
        })
        vw_recycler.itemAnimator = SlideInLeftAnimator()
    }

    private fun setupObservers() {
        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            vw_refresh_articles.isRefreshing = it
        })

        viewModel.snackbarText.observe(viewLifecycleOwner, Observer { message ->
            home_root_view.showSnackbar(message)
        })

        viewModel.items.observe(viewLifecycleOwner, Observer { articles ->
            adapter.setItems(articles)
        })

        viewModel.loadMoreItems.observe(viewLifecycleOwner, Observer { articles ->
            adapter.addItems(articles)
        })

        vw_refresh_articles.setOnRefreshListener {
            viewModel.refreshArticles()
        }

        btn_dismiss_all.setOnClickListener {
            val size = adapter.getItems().size
            adapter.getItems().clear()
            adapter.notifyItemRangeRemoved(0, size)
        }
    }

    override fun onArticleClicked(item: Article) {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment2)

        if (navHostFragment is DetailFragment) {
            navHostFragment.updateFragment(item)
        }

        if (!activity.isScreenLandscape() && activity is MainActivity) {
            (activity as MainActivity).showPortraitVisibility(
                homeVisible = false,
                detailVisible = true
            )
        }

        item.read = true
        adapter.notifyDataSetChanged()
    }

    override fun onArticleDismissed(item: Article) {
        val position = adapter.getItems().indexOf(item)
        if (position != Constants.INDEX_NOT_FOUND) {
            adapter.getItems().removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    fun isRecyclerBottom(dy: Int, layoutManager: LinearLayoutManager): Boolean {
        var isEndless = false
        if (dy > 0) {
            if (layoutManager.childCount + layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount) {
                isEndless = true
            }
        }
        return isEndless
    }
}