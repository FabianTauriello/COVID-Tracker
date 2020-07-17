package io.github.fabiantauriello.covidtracker

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
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

}