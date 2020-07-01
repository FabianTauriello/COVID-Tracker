package io.github.fabiantauriello.covidtracker.view

import android.view.View

// navigator used to drive UI navigation from View Models
interface OpenCountryStatsNavigator {
    fun navigate(view: View)
}