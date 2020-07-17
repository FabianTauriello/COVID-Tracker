package io.github.fabiantauriello.covidtracker.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.databinding.FragmentCountryStatsBinding
import io.github.fabiantauriello.covidtracker.util.showAPIError
import io.github.fabiantauriello.covidtracker.viewmodels.CountryStatsViewModel
import io.github.fabiantauriello.covidtracker.viewmodels.CountryStatsViewModelFactory

class CountryStatsFragment : Fragment() {

    private val LOG_TAG = CountryStatsFragment::class.simpleName

    private val args: CountryStatsFragmentArgs by navArgs()
    private lateinit var countryStatsViewModel: CountryStatsViewModel

    private lateinit var binding: FragmentCountryStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // tell activity that this fragment has menu options it wants to add
        setHasOptionsMenu(true)

        val countrySelected = args.country

        // get country selected from previous screen and set it to be the app bar title
        (activity as AppCompatActivity).supportActionBar?.title = countrySelected.countryName

        countryStatsViewModel =
            ViewModelProvider(this, CountryStatsViewModelFactory(countrySelected)).get(
                CountryStatsViewModel::class.java
            )

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_stats, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = countryStatsViewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.fragment_country_list_heading)
        super.onDetach()
    }

}