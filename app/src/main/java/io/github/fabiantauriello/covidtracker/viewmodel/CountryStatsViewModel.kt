package io.github.fabiantauriello.covidtracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.model.SummaryStats
import io.github.fabiantauriello.covidtracker.model.VolleyRepository

class CountryStatsViewModel(
    private val countryName: String,
    private val repository: VolleyRepository = VolleyRepository()
) : ViewModel() {

    private val LOG_TAG = this::class.simpleName

    init {
        Log.d(LOG_TAG, "CountryStatsViewModel init")
        fetchCountrySummary()
    }

    private val countryLiveData: MutableLiveData<SummaryStats?> = repository.countrySummary

    fun getCountryLiveData(): LiveData<SummaryStats?> {
        return countryLiveData
    }

    fun fetchCountrySummary() {
        repository.fetchCountrySummary(countryName)
    }

}