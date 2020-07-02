package io.github.fabiantauriello.covidtracker.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.model.*
import io.github.fabiantauriello.covidtracker.view.OpenCountryListNavigator
import io.github.fabiantauriello.covidtracker.view.OpenCountryStatsNavigator

class GlobalStatsViewModel(
    private val repository: VolleyRepository = VolleyRepository()
) : ViewModel() {

    var navigator: OpenCountryListNavigator? = null

    init {
        fetchGlobalSummary()
    }

    private val globalLiveData: MutableLiveData<SummaryStats?> = repository.globalSummary

    // return live data instead of mutable so ui knows it can't change the data.
    fun getGlobalLiveData(): LiveData<SummaryStats?> {
        return globalLiveData
    }

    fun fetchGlobalSummary() {
        repository.fetchGlobalSummary()
    }

    fun onButtonClicked() {
        navigator?.let {
            navigator?.navigate()
        }
    }

}