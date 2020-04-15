package com.example.reddittop50.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddittop50.R
import com.example.reddittop50.misc.Constants
import com.example.reddittop50.model.Article
import com.example.reddittop50.ui.ViewModelFactory
import com.example.reddittop50.ui.home.HomeFragment
import com.example.reddittop50.ui.main.paging.ArticlesPagedAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ArticlesPagedAdapter.IOnArticleListener {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val adapter = ArticlesPagedAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar()
        setupDrawer()
        setupAdapter()
        setupObservers()
    }

    private fun setupDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setupAdapter() {
        val layoutManager = LinearLayoutManager(this)
        vw_recycler.layoutManager = layoutManager
        vw_recycler.adapter = adapter
    }

    private fun setupObservers() {
        addArticlesListObserver()

        viewModel.dataLoading.observe(this, Observer {
            vw_refresh_articles.isRefreshing = it
        })

        vw_refresh_articles.setOnRefreshListener {
            removeArticlesListObserver()
            viewModel.refreshArticles()
            addArticlesListObserver()
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onArticleClicked(item: Article) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager!!.fragments[0]

        if (currentFragment is HomeFragment) {
            currentFragment.updateFragment(item)
        }

        item.read = true
        adapter.notifyDataSetChanged()

        closeDrawer()
    }

    override fun onArticleDismissed(item: Article) {
        item.visible = false

        // The code below only dismisses the item visually, but the immutable list is still containing it.
        val position = adapter.currentList?.indexOf(item) ?: Constants.INDEX_NOT_FOUND
        if (position != Constants.INDEX_NOT_FOUND) {
            adapter.notifyItemChanged(position)
        }
    }

    private fun closeDrawer() {
        drawer_layout?.closeDrawer(GravityCompat.START)
    }

    private fun removeArticlesListObserver() {
        viewModel.items.removeObservers(this)
    }

    private fun addArticlesListObserver() {
        viewModel.items.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
