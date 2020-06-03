package io.github.fabiantauriello.covidtracker.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import io.github.fabiantauriello.covidtracker.presenter.*
import io.github.fabiantauriello.covidtracker.view.CountryListFragment
import java.lang.reflect.Array

object DataManager {

    private val LOG_TAG = DataManager::class.simpleName

    private const val BASE_URL = "https://api.covid19api.com/"
    private const val SUMMARY_ENDPOINT = "summary"
    private const val COUNTRIES_ENDPOINT = "countries"

    private val gson = Gson()

    fun fetchSummaryPair(
        presenter: GlobalStatsPresenter,
        context: Context
    ) {
        val request = JsonObjectRequest(Request.Method.GET, BASE_URL + SUMMARY_ENDPOINT, null,
            Response.Listener { response ->
                // get global summary
                val jsonString = response.getJSONObject("Global").toString()
                val globalSummary = gson.fromJson(jsonString, SummaryStats::class.java)

                // get list of country names
                val countryNames = ArrayList<String>()
                val jsonArray = response.getJSONArray("Countries")
                for (i in 0 until jsonArray.length()) {
                    val country = jsonArray.getJSONObject(i)
                    val countryName = country.getString("Country")
                    countryNames.add(countryName)
                }

                val pair = Pair(globalSummary, countryNames.toTypedArray())
                presenter.onFetchSummaryStatsResponseSuccess(pair)
            },
            Response.ErrorListener { error ->
                presenter.onFetchSummaryStatsResponseError(error.toString())
            }
        )
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    fun fetchCountrySummary(
        presenter: CountryStatsPresenter,
        context: Context,
        countryName: String
    ) {
        val request = JsonObjectRequest(Request.Method.GET, BASE_URL + SUMMARY_ENDPOINT, null,
            Response.Listener { response ->
                val jsonArray = response.getJSONArray("Countries")
                var countrySummaryStats: SummaryStats? = null
                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    if (item.getString("Country") == countryName) {
                        countrySummaryStats =
                            gson.fromJson(item.toString(), SummaryStats::class.java)
                        break
                    }
                }
                presenter.onFetchSummaryStatsResponseSuccess(countrySummaryStats)
            },
            Response.ErrorListener { error ->
                presenter.onFetchSummaryStatsResponseError(error.toString())
            }
        )
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

}