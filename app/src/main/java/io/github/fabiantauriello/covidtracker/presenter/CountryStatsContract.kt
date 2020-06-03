package io.github.fabiantauriello.covidtracker.presenter

import android.content.Context
import io.github.fabiantauriello.covidtracker.model.SummaryStats

interface CountryStatsContract {
    interface Presenter {
        fun fetchCountrySummary(context: Context, countryName: String = "")
        fun onFetchSummaryStatsResponseSuccess(summaryStats: SummaryStats?)
        fun onFetchSummaryStatsResponseError(errorMessage: String)
    }

    interface View {
        fun updateSummaryStats(summary: SummaryStats?)
        fun showAPIError()
        fun stopProgressBar()
    }
}