package io.github.fabiantauriello.covidtracker.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.model.VolleyRepository
import io.github.fabiantauriello.covidtracker.view.OpenCountryStatsNavigator

class CountryListViewModel(
    private val repository: VolleyRepository = VolleyRepository()
) : ViewModel() {

    private val LOG_TAG = this::class.simpleName

    var navigator: OpenCountryStatsNavigator? = null

    init {
        Log.d(LOG_TAG, "CountryListViewModel init")
        fetchCountryList()
    }

    private val countryListLiveData: MutableLiveData<ArrayList<String>?> = repository.countryList

    fun getCountryListLiveData(): LiveData<ArrayList<String>?> {
        return countryListLiveData
    }

    fun fetchCountryList() {
        repository.fetchCountryList()
    }

    fun onCountryClicked(view: View) {
        Log.d(LOG_TAG, "onCountryClicked: ")
        navigator?.let {
            navigator?.navigate(view)
        }
    }

}