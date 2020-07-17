package io.github.fabiantauriello.covidtracker.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.DataType
import io.github.fabiantauriello.covidtracker.domain.CountryData
import io.github.fabiantauriello.covidtracker.repositories.Repository
import io.github.fabiantauriello.covidtracker.ui.OpenCountryStatsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class CountryListViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val LOG_TAG = this::class.simpleName

    var navigator: OpenCountryStatsNavigator? = null

    private val _countryLiveData = repository.countryListLiveData

    val countryLiveData: LiveData<List<CountryData>>
        get() = _countryLiveData

    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.refreshData(DataType.COUNTRY)
            } catch (networkError: IOException) {

            }
        }
    }

    fun onCountryClicked(view: View) {
        navigator?.let {
            navigator?.navigate(view)
        }
    }

}