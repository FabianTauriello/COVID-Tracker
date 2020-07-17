package io.github.fabiantauriello.covidtracker.util

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.database.CountryDataDao_Impl
import io.github.fabiantauriello.covidtracker.database.CountryEntity
import io.github.fabiantauriello.covidtracker.database.GlobalEntity
import io.github.fabiantauriello.covidtracker.domain.CountryData
import io.github.fabiantauriello.covidtracker.domain.GlobalData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


fun showAPIError(context: Context) {
    Toast.makeText(
        context,
        context.getString(R.string.data_retrieval_error_msg),
        Toast.LENGTH_LONG
    ).show()
}

/**
 * When you return a LiveData from a Dao in Room it makes the query asynchronously, and Room sets
 * the LiveData#value lazily after you kick off the query by observing the LiveData.
 *
 * For unit tests I want the behavior to be synchronous, so I must block the test thread and
 * wait for the value to be passed to the observer, then grab it from there and then I can
 * assert on it.
 *
 * This extension function will make Room queries synchronous for testing purposes. It will block
 * the thread it's being called from (testing thread), and wait for the data to be updated.
 */
fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { T ->
        // on changed...
        value = T
        latch.countDown()
    }

    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)

    // return query result (without livedata wrapping)
    return value
}

fun GlobalEntity.asDomainModel(): GlobalData = GlobalData(
    newCases = newCases,
    totalCases = totalCases,
    newDeaths = newDeaths,
    totalDeaths = totalDeaths,
    newRecovered = newRecovered,
    totalRecovered = totalRecovered
)

fun GlobalData.asDatabaseModels(): GlobalEntity = GlobalEntity(
    newCases = newCases,
    totalCases = totalCases,
    newDeaths = newDeaths,
    totalDeaths = totalDeaths,
    newRecovered = newRecovered,
    totalRecovered = totalRecovered
)

fun List<CountryData>.asDatabaseModels(): List<CountryEntity> {
    return map {
        CountryEntity(
            countryName = it.countryName,
            newCases = it.newCases,
            totalCases = it.totalCases,
            newDeaths = it.newDeaths,
            totalDeaths = it.totalDeaths,
            newRecovered = it.newRecovered,
            totalRecovered = it.totalRecovered
        )
    }
}

fun List<CountryEntity>.asDomainModels(): List<CountryData> {
    return map {
        CountryData(
            countryName = it.countryName,
            newCases = it.newCases,
            totalCases = it.totalCases,
            newDeaths = it.newDeaths,
            totalDeaths = it.totalDeaths,
            newRecovered = it.newRecovered,
            totalRecovered = it.totalRecovered
        )
    }
}

fun CountryEntity.asDomainModel(): CountryData {
    return CountryData(
        this.countryName,
        this.newCases,
        this.totalCases,
        this.newDeaths,
        this.totalDeaths,
        this.newRecovered,
        this.totalRecovered
    )
}



