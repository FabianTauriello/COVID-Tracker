package io.github.fabiantauriello.covidtracker.presenter

import android.content.Context
import io.github.fabiantauriello.covidtracker.model.DataManager
import io.github.fabiantauriello.covidtracker.model.SummaryStats

class GlobalStatsPresenter : BasePresenter<GlobalStatsContract.View>(),
    GlobalStatsContract.Presenter {

    override fun fetchSummaryPair(context: Context, countryName: String) {
        DataManager.fetchSummaryPair(this, context)
    }

    override fun onFetchSummaryStatsResponseSuccess(pair: Pair<SummaryStats, Array<String>>) {
        getView()?.updateData(pair.first)
        getView()?.configureButton(pair.second)
        getView()?.stopProgressBar()
    }

    override fun onFetchSummaryStatsResponseError(errorMessage: String) {
        getView()?.showAPIError()
        getView()?.stopProgressBar()
    }

}