package com.example.reddittop50

import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.reddittop50.ui.home.HomeFragment
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainFragmentSimpleTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<HomeFragment>()
    }

    @Test
    fun swipe() {
        onView(ViewMatchers.withId(R.id.vw_refresh_articles)).perform(ViewActions.swipeDown())
        scenario.recreate()
    }

    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun perform(uiController: UiController?, view: View?) {
                uiController?.loopMainThreadForAtLeast(delay)
            }

            override fun getConstraints(): Matcher<View> {
                return isRoot()
            }

            override fun getDescription(): String {
                return "wait for " + delay + " milliseconds"
            }
        }
    }

}