package com.test.tix.detail

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.test.tix.DummyData
import com.test.tix.R
import com.test.tix.core.app.model.ItemUser
import com.test.tix.core.app.utils.testing.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class DetailUserTest {

    private val dummyUser: ItemUser = DummyData.dummyUser
    private lateinit var scenarioRule: ActivityScenario<DetailUserActivity>
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadUserDetail() {
        scenarioRule = ActivityScenario.launch(
            Intent(context, DetailUserActivity::class.java).apply {
                putExtra(DetailUserActivity.EXTRA_ID_USER, dummyUser)
            }
        )
        Espresso.onView(withId(R.id.tv_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_followers_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_following_value)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.tv_city)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.rv_repos)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.iv_item_photo_detail)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}