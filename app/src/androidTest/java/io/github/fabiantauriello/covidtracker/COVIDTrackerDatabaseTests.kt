package io.github.fabiantauriello.covidtracker


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.github.fabiantauriello.covidtracker.database.*
import io.github.fabiantauriello.covidtracker.util.blockingObserve
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.junit.Assert.*

// The @RunWith annotation identifies the test runner, which is the program
// that sets up and executes the tests
@RunWith(AndroidJUnit4::class)
class COVIDTrackerDatabaseTests {

    private lateinit var globalDataDao: GlobalDataDao
    private lateinit var countryDataDao: CountryDataDao
    private lateinit var db: COVIDTrackerDatabase

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    // During setup, the function annotated with @Before is executed, and it creates an
    // in-memory COVIDTrackerDatabase with entities. "In-memory" means that this database is
    // not saved on the file system and will be deleted after the tests run.
    //
    // Also when building the in-memory database, the code calls another test-specific
    // method, allowMainThreadQueries. By default, you get an error if you try to run queries
    // on the main thread. This method allows you to run tests on the main thread, which you
    // should only do during testing.
    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, COVIDTrackerDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        globalDataDao = db.globalDataDao
        countryDataDao = db.countryDataDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    // SummaryStatsDao tests

    @Test
    fun insertAndGetGlobalSummaryStats() {
        val expectedGlobalSummaryStats = GlobalEntity(
            id = 1,
            newCases = "1000",
            totalCases = "2000",
            newDeaths = "3000",
            totalDeaths = "4000",
            newRecovered = "5000",
            totalRecovered = "6000",
            isGlobal = true
        )

        globalDataDao.insert(expectedGlobalSummaryStats)

        val retrievedGlobalSummaryStats = globalDataDao.getGlobalData(true).blockingObserve()

        assertEquals(expectedGlobalSummaryStats, retrievedGlobalSummaryStats)
    }

    @Test
    fun insertAndGetCountrySummaryStats() {
        val expectedCountrySummaryStats = GlobalEntity(
            id = 0,
            newCases = "1000",
            totalCases = "2000",
            newDeaths = "3000",
            totalDeaths = "4000",
            newRecovered = "5000",
            totalRecovered = "6000",
            isGlobal = false
        )

        globalDataDao.insert(expectedCountrySummaryStats)

        val retrievedCountrySummaryStats = globalDataDao.getGlobalData(false).blockingObserve()

        assertEquals(expectedCountrySummaryStats, retrievedCountrySummaryStats)
    }

    // CountryDao tests

    @Test
    fun insertAndGetCountryList() {
        val countryList = mutableListOf<CountryEntity>()
        countryList.add(CountryEntity(1, "Australia"))
        countryList.add(CountryEntity(2, "Belgium"))

        countryDataDao.insertAll(countryList)

        val retrievedCountryList = countryDataDao.getAllCountries().blockingObserve()

        assertArrayEquals(countryList.toTypedArray(), retrievedCountryList?.toTypedArray())
    }

}