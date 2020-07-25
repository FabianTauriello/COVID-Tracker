package io.github.fabiantauriello.covidtracker

import android.app.ActionBar
import android.widget.AutoCompleteTextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.fabiantauriello.covidtracker.ui.CountryListAdapter
import io.github.fabiantauriello.covidtracker.ui.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UITests {

    /*

    EXAMPLE TEST:

    onView(withId(R.id.my_view))                 // withId(R.id.my_view) is a ViewMatcher
        .perform(click())                        // click() is a ViewAction
        .check(matches(isDisplayed()))           // matches(isDisplayed()) is a ViewAssertion

     */

    @Before
    fun setUp() {
        // launch MainActivity
        ActivityScenario.launch(MainActivity::class.java)
    }

    // Make sure the button on the home fragment (global stats) opens the country list fragment
    @Test
    fun openCountryList() {
        // click on button
        onView(withId(R.id.btn_country_stats)).perform(click())

        // check that country list fragment is displayed
        onView(withId(R.id.rv_countries_container)).check(matches(isDisplayed()))
    }

    // Make sure the country stats fragment opens after a user presses the first item in an unfiltered country list
    @Test
    fun openCountryStatsForUnfilteredListItem() {
        onView(withId(R.id.btn_country_stats)).perform(click())

        onView(withId(R.id.rv_countries)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CountryListAdapter.CountryItemViewHolder>(
                0,
                click()
            )
        )

        // check that country stats fragment is displayed
        onView(withId(R.id.country_stats_container)).check(matches(isDisplayed()))
    }

    // Make sure the country stats fragment opens after a user presses the first item in a filtered country list
    @Test
    fun openCountryStatsForFilteredListItem() {
        onView(withId(R.id.btn_country_stats)).perform(click())

        onView(withId(R.id.action_search)).perform(click())

        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("Belgium"))

        onView(withId(R.id.rv_countries)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CountryListAdapter.CountryItemViewHolder>(
                0,
                click()
            )
        )

        // check that country stats fragment is displayed
        onView(withId(R.id.country_stats_container)).check(matches(isDisplayed()))

    }

}