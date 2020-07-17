package io.github.fabiantauriello.covidtracker.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.fabiantauriello.covidtracker.domain.CountryData

class CountryStatsViewModelFactory(private val countrySelected: CountryData) : ViewModelProvider.Factory {

    val LOG_TAG = this::class.simpleName

    init {
        Log.d(LOG_TAG, "CountryStatsViewModelFactory init")
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryStatsViewModel::class.java)) {
            return CountryStatsViewModel(countrySelected) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}