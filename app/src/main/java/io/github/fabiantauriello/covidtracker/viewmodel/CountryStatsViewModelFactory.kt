package io.github.fabiantauriello.covidtracker.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CountryStatsViewModelFactory(private val countryName: String) : ViewModelProvider.Factory {

    val LOG_TAG = this::class.simpleName

    init {
        Log.d(LOG_TAG, "CountryStatsViewModelFactory init")
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryStatsViewModel::class.java)) {
            return CountryStatsViewModel(countryName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}