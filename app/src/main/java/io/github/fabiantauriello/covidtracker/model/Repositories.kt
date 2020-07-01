package io.github.fabiantauriello.covidtracker.model

import android.app.Application
import android.content.Context
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson


interface Repositories {

    interface Volley {
        fun fetchGlobalSummary()
        fun fetchCountryList()
        fun fetchCountrySummary(countryName: String)
    }

}