package io.github.fabiantauriello.covidtracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.fabiantauriello.covidtracker.database.DataType
import io.github.fabiantauriello.covidtracker.domain.GlobalData
import io.github.fabiantauriello.covidtracker.repositories.Repository
import io.github.fabiantauriello.covidtracker.ui.OpenCountryListNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class GlobalStatsViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val LOG_TAG = this::class.simpleName

    var navigator: OpenCountryListNavigator? = null

    /**
     * This is private to avoid exposing a way to set this value to observers.
     */
    private val _globalLiveData = repository.globalLiveData

    /**
     * Views should use this to get access to the data.
     */
    val globalLiveData: LiveData<GlobalData>
        get() = _globalLiveData

    init {
        refreshDataFromRepository()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun refreshDataFromRepository() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                repository.refreshData(DataType.GLOBAL)
            } catch (networkError: IOException) {

            }
        }
    }

    fun onButtonClicked() {
        navigator?.let {
            navigator?.navigate()
        }
    }

}