package io.github.fabiantauriello.covidtracker.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.fabiantauriello.covidtracker.app.COVIDTracker

/**
 * My Room database
 *
 * The class is abstract, because Room creates the implementation for me.
 */
@Database(
    entities = [GlobalEntity::class, CountryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class COVIDTrackerDatabase : RoomDatabase() {

    abstract val globalDataDao: GlobalDataDao
    abstract val countryDataDao: CountryDataDao

    companion object {

        // This will keep a reference to the database, once one has been created
        @Volatile
        private var INSTANCE: COVIDTrackerDatabase? = null

        fun getInstance(): COVIDTrackerDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        COVIDTracker.applicationContext(),
                        COVIDTrackerDatabase::class.java,
                        "covid_tracker_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}