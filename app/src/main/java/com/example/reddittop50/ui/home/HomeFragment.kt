package com.example.reddittop50.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.model.Article
import com.example.reddittop50.ui.ViewModelFactory
import com.example.reddittop50.ui.main.paging.ArticlesPagedAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home), ArticlesPagedAdapter.IOnArticleListener {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory()
    }

    private val adapter = ArticlesPagedAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar()
        setupAdapter()
        setupObservers()
    }


    private fun setupToolbar() {
        setHasOptionsMenu(true)
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(activity)
        vw_recycler.layoutManager = layoutManager
        vw_recycler.adapter = adapter
    }

    private fun setupObservers() {
        addArticlesListObserver()

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer {
            vw_refresh_articles.isRefreshing = it
        })

        vw_refresh_articles.setOnRefreshListener {
            removeArticlesListObserver()
            viewModel.refreshArticles()
            addArticlesListObserver()
        }
    }

    override fun onArticleClicked(item: Article) {
        val action = HomeFragmentDirections.actionMainFragmentToDetailFragment(item)
        view?.findNavController()?.navigate(action)

        item.read = true
        adapter.notifyDataSetChanged()
    }

    override fun onArticleDismissed(item: Article) {
        item.visible = false

        // The code below only dismisses the item visually, but the immutable list is still containing it.
        val position = adapter.currentList?.indexOf(item) ?: Constants.INDEX_NOT_FOUND
        if (position != Constants.INDEX_NOT_FOUND) {
            adapter.notifyItemChanged(position)
        }
    }

    private fun removeArticlesListObserver() {
        viewModel.items.removeObservers(this)
    }

    private fun addArticlesListObserver() {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}