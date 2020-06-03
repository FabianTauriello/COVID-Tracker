package io.github.fabiantauriello.covidtracker.presenter

import android.content.Context
import io.github.fabiantauriello.covidtracker.model.SummaryStats

interface GlobalStatsContract {
    interface Presenter {
        fun fetchSummaryPair(context: Context, countryName: String = "")
        fun onFetchSummaryStatsResponseSuccess(pair: Pair<SummaryStats, Array<String>>)
        fun onFetchSummaryStatsResponseError(errorMessage: String)
    }

    interface View {
        fun updateData(summaryStats: SummaryStats?)
        fun configureButton(countryList: Array<String>)
        fun stopProgressBar()
        fun showAPIError()
    }
}