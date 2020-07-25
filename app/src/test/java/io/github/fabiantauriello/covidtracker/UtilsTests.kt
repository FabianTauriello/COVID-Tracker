package io.github.fabiantauriello.covidtracker

import io.github.fabiantauriello.covidtracker.database.CountryEntity
import io.github.fabiantauriello.covidtracker.database.GlobalEntity
import io.github.fabiantauriello.covidtracker.domain.CountryData
import io.github.fabiantauriello.covidtracker.domain.GlobalData
import io.github.fabiantauriello.covidtracker.util.asDatabaseModel
import io.github.fabiantauriello.covidtracker.util.asDomainModel
import io.github.fabiantauriello.covidtracker.util.asDomainModels
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.ParameterizedType

class UtilsTests {

    private lateinit var globalEntity: GlobalEntity
    private lateinit var globalData: GlobalData
    private lateinit var countryEntityList: List<CountryEntity>
    private lateinit var countryDataList: List<CountryData>

    @Before
    fun setup() {
        globalEntity = GlobalEntity(
            1,
            "1000",
            "2000",
            "3000",
            "4000",
            "5000",
            "6000"
        )
        globalData = GlobalData(
            "1000",
            "2000",
            "3000",
            "4000",
            "5000",
            "6000"
        )
        countryEntityList = listOf(
            CountryEntity("Australia", "1000", "2000", "3000", "4000", "5000", "6000")
        )
        countryDataList = listOf(
            CountryData("Algeria", "1000", "2000", "3000", "4000", "5000", "6000")
        )
    }

    @Test
    fun checkGlobalEntityConvertsToGlobalDataType() {
        val convertedObject = globalEntity.asDomainModel()

        assertThat(convertedObject, instanceOf(GlobalData::class.java))
    }

    @Test
    fun checkGlobalDataConvertsToGlobalEntityType() {
        val convertedObject = globalData.asDatabaseModel()

        assertThat(convertedObject, instanceOf(GlobalEntity::class.java))
    }

    @Test
    fun checkCountryEntityListConvertsToCountryDataList() {
        val convertedObject = countryEntityList.asDomainModels()

        assertThat(convertedObject[0], instanceOf(CountryData::class.java))
    }

    @Test
    fun checkCountryDataListConvertsToCountryEntityList() {
        val convertedObject = countryDataList.asDatabaseModel()

        assertThat(convertedObject[0], instanceOf(CountryEntity::class.java))
    }

}