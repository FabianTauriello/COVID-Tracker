package io.github.fabiantauriello.covidtracker.presenter

import android.content.Context
import io.github.fabiantauriello.covidtracker.model.DataManager
import io.github.fabiantauriello.covidtracker.model.SummaryStats

class CountryStatsPresenter : BasePresenter<CountryStatsContract.View>(),
    CountryStatsContract.Presenter {

    override fun fetchCountrySummary(context: Context, countryName: String) {
        DataManager.fetchCountrySummary(this, context, countryName)
    }

    override fun onFetchSummaryStatsResponseSuccess(summaryStats: SummaryStats?) {
        getView()?.updateSummaryStats(summaryStats)
        getView()?.stopProgressBar()
    }

    override fun onFetchSummaryStatsResponseError(errorMessage: String) {
        getView()?.showAPIError()
        getView()?.stopProgressBar()
    }

}