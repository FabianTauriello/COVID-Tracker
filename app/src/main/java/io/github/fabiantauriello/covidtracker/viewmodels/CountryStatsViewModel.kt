package io.github.fabiantauriello.covidtracker.viewmodels

import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.domain.CountryData

class CountryStatsViewModel(
    private val countrySelected: CountryData
) : ViewModel() {

    private val _countryData = countrySelected

    val countryData: CountryData
        get() = _countryData

}