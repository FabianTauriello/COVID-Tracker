package io.github.fabiantauriello.covidtracker.model

import com.google.gson.annotations.SerializedName

data class SummaryStats(
    @SerializedName("NewConfirmed")
    val newCases: String,
    @SerializedName("TotalConfirmed")
    val totalCases: String,
    @SerializedName("NewDeaths")
    val newDeaths: String,
    @SerializedName("TotalDeaths")
    val totalDeaths: String,
    @SerializedName("NewRecovered")
    val newRecovered: String,
    @SerializedName("TotalRecovered")
    val totalRecovered: String
) {

}