package io.github.fabiantauriello.covidtracker.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class SummaryResponse(
    @SerializedName("Global")
    val global: GlobalData,
    @SerializedName("Countries")
    val countries: List<CountryData>
)

data class GlobalData(
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
)

/**
 * Parcelable so this object can be passed between CountryListFragment and CountryStatsFragment
 */
@Parcelize
data class CountryData(
    @SerializedName("Country")
    val countryName: String,
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
) : Parcelable
