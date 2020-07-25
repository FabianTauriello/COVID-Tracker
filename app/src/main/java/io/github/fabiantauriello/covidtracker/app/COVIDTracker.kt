package io.github.fabiantauriello.covidtracker.app

import android.app.Application
import android.content.Context

class COVIDTracker : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: COVIDTracker? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

}