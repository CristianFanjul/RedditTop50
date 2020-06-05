package com.example.reddittop50.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.misc.showSnackbar
import com.example.reddittop50.model.Article
import com.example.reddittop50.ui.main.ArticlesAdapter
import com.example.reddittop50.ui.main.IOnArticleListener
import dagger.android.support.DaggerFragment
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : DaggerFragment(R.layout.fragment_home), IOnArticleListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> {
        viewModelFactory
    }

    private val adapter = ArticlesAdapter(this)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar()
        setupAdapter()
        setupObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (adapter.getItems().isNullOrEmpty()) {
            viewModel.loadInitialArticles()
        }
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
            view.showSnackbar(message)
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
        item.read = true
        adapter.notifyDataSetChanged()

        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailFragment(item)
        view?.findNavController()?.navigate(action)
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