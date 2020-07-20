package io.github.fabiantauriello.covidtracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GlobalDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(globalEntity: GlobalEntity)

    @Query("SELECT * FROM global_data")
    fun getGlobalData(): LiveData<GlobalEntity>

}

@Dao
interface CountryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countryEntities: List<CountryEntity>)

    @Query("SELECT * FROM country_data")
    fun getAllCountries(): LiveData<List<CountryEntity>>

}