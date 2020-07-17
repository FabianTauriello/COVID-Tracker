package io.github.fabiantauriello.covidtracker.network

import io.github.fabiantauriello.covidtracker.domain.SummaryResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * A retrofit service to fetch COVID data.
 */
interface COVIDTrackerService {
    @GET("/summary")
    suspend fun getSummary(): SummaryResponse // TODO consider wrapping this with Response for better error handling
}

/**
 * Main entry point for network access.
 */
object COVIDTrackerNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.covid19api.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: COVIDTrackerService = retrofit.create(COVIDTrackerService::class.java)

}