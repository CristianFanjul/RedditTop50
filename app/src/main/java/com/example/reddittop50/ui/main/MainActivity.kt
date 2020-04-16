package com.example.reddittop50.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.reddittop50.R
import com.example.reddittop50.misc.isLandscape
import com.example.reddittop50.misc.isScreenLandscape
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var lastOrientation: Int = Configuration.ORIENTATION_PORTRAIT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lastOrientation = resources.configuration.orientation
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (isScreenLandscape()) {
            showLandscapeMode()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (!lastOrientation.isLandscape()) {
            // Handles the back pressed to hide the details if they are being displayed.
            if (detail_fragment_container.isVisible) {
                showPortraitVisibility(homeVisible = true, detailVisible = false)
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun showLandscapeMode() {
        home_fragment_container.isVisible = true
        detail_fragment_container.isVisible = true
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // Check old status config: if comes from landscape show home fragment
        val homeVisible =
            if (lastOrientation.isLandscape()) true else home_fragment_container.isVisible
        val detailVisible =
            if (lastOrientation.isLandscape()) false else detail_fragment_container.isVisible

        lastOrientation = newConfig.orientation
        super.onConfigurationChanged(newConfig)

        if (isScreenLandscape()) {
            showLandscapeMode()
        } else {
            showPortraitVisibility(homeVisible, detailVisible)
        }
    }

    fun showPortraitVisibility(homeVisible: Boolean, detailVisible: Boolean) {
        home_fragment_container.isVisible = homeVisible
        detail_fragment_container.isVisible = detailVisible
    }
}
