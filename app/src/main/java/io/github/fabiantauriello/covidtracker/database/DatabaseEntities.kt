package io.github.fabiantauriello.covidtracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an entity in the database for storing key data on
 * COVID for a single country, or worlwide
 */
@Entity(tableName = "global_data")
data class GlobalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "new_cases")
    var newCases: String,

    @ColumnInfo(name = "total_cases")
    val totalCases: String,

    @ColumnInfo(name = "new_deaths")
    val newDeaths: String,

    @ColumnInfo(name = "total_deaths")
    val totalDeaths: String,

    @ColumnInfo(name = "new_recovered")
    val newRecovered: String,

    @ColumnInfo(name = "total_recovered")
    val totalRecovered: String
)

/**
 * Represents an entity in the database for storing countries that
 * can be inspected on its COVID data
 */
@Entity(tableName = "country_data")
data class CountryEntity(

    @PrimaryKey
    @ColumnInfo(name = "country_name")
    val countryName: String,

    @ColumnInfo(name = "new_cases")
    val newCases: String,

    @ColumnInfo(name = "total_cases")
    val totalCases: String,

    @ColumnInfo(name = "new_deaths")
    val newDeaths: String,

    @ColumnInfo(name = "total_deaths")
    val totalDeaths: String,

    @ColumnInfo(name = "new_recovered")
    val newRecovered: String,

    @ColumnInfo(name = "total_recovered")
    val totalRecovered: String
)