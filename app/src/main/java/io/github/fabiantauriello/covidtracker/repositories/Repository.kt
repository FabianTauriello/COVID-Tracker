package io.github.fabiantauriello.covidtracker.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import io.github.fabiantauriello.covidtracker.DataType
import io.github.fabiantauriello.covidtracker.database.COVIDTrackerDatabase
import io.github.fabiantauriello.covidtracker.domain.CountryData
import io.github.fabiantauriello.covidtracker.domain.GlobalData
import io.github.fabiantauriello.covidtracker.network.COVIDTrackerNetwork
import io.github.fabiantauriello.covidtracker.util.asDatabaseModels
import io.github.fabiantauriello.covidtracker.util.asDomainModel
import io.github.fabiantauriello.covidtracker.util.asDomainModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for accessing remote and local data sources.
 * (fetches COVID data from the network (Volley) and stores them on disk (Room))
 */
class Repository { // TODO consider making different repositories.

    private val LOG_TAG = this::class.simpleName

    private val database = COVIDTrackerDatabase.getInstance()

    // get global data from local db
    var globalLiveData: LiveData<GlobalData> =
        Transformations.map(database.globalDataDao.getGlobalData()) {
            Log.d(LOG_TAG, "globalLiveData Transformations called")
            it?.asDomainModel()
        }

    // get country data from local db
    var countryListLiveData: LiveData<List<CountryData>> =
        Transformations.map(database.countryDataDao.getAllCountries()) {
            Log.d(LOG_TAG, "countryLiveData Transformations called")
            it.asDomainModels()
        }

    /**
     * Refresh the summary stats stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshData(dataType: DataType) {
        withContext(Dispatchers.IO) {
            when (dataType) {
                DataType.GLOBAL -> {
                    val summaryResponse = COVIDTrackerNetwork.service.getSummary()
                    database.globalDataDao.insert(summaryResponse.global.asDatabaseModels())
                }
                DataType.COUNTRY -> {
                    val summaryResponse = COVIDTrackerNetwork.service.getSummary()
                    database.countryDataDao.insertAll(summaryResponse.countries.asDatabaseModels())
                }
            }
        }
    }


}