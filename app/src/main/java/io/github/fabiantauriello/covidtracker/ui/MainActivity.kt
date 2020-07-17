package io.github.fabiantauriello.covidtracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.fabiantauriello.covidtracker.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = getString(R.string.fragment_global_stats_heading)

    }

}
