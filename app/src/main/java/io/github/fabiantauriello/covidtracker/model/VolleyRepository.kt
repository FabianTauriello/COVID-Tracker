package io.github.fabiantauriello.covidtracker.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import io.github.fabiantauriello.covidtracker.app.MyApplication

class VolleyRepository() : Repositories.Volley {

    private val LOG_TAG = this::class.simpleName

    private val URL = "https://api.covid19api.com/summary"

    private val context: Context = MyApplication.applicationContext()
    private val gson = Gson()

    var globalSummary: MutableLiveData<SummaryStats?> = MutableLiveData()
    var countryList: MutableLiveData<ArrayList<String>?> = MutableLiveData()
    var countrySummary: MutableLiveData<SummaryStats?> = MutableLiveData()

    override fun fetchGlobalSummary() {
        Log.d(LOG_TAG, "fetchGlobalSummary called")
        val request = JsonObjectRequest(
            Request.Method.GET, URL, null,
            Response.Listener { response ->
                // get global summary
                val jsonString = response.getJSONObject("Global").toString()
                // update global summary property
                globalSummary.value = gson.fromJson(jsonString, SummaryStats::class.java)
            },
            Response.ErrorListener { error ->
                globalSummary.value = null
            }
        )
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    override fun fetchCountryList() {
        Log.d(LOG_TAG, "fetchCountryList called")
        val request = JsonObjectRequest(Request.Method.GET, URL, null,
            Response.Listener { response ->
                // get list of country names
                val countryNames = ArrayList<String>()
                val jsonArray = response.getJSONArray("Countries")
                for (i in 0 until jsonArray.length()) {
                    val country = jsonArray.getJSONObject(i)
                    val countryName = country.getString("Country")
                    countryNames.add(countryName)
                }
                countryList.value = countryNames
                Log.d(LOG_TAG, "fetchCountryList success")
            },
            Response.ErrorListener { error ->
                countryList.value = null
                Log.d(LOG_TAG, "fetchCountryList failed")
            }
        )
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

    override fun fetchCountrySummary(countryName: String) {
        Log.d(LOG_TAG, "fetchCountrySummary called")
        val request = JsonObjectRequest(Request.Method.GET, URL, null,
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
                // update country summary property
                countrySummary.value = countrySummaryStats
            },
            Response.ErrorListener { error ->
                countrySummary.value = null
            }
        )
        VolleySingleton.getInstance(context).addToRequestQueue(request)
    }

}