package io.github.fabiantauriello.covidtracker.view

import android.os.Bundle
import android.util.Log
import android.util.LogPrinter
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.databinding.FragmentCountryStatsBinding
import io.github.fabiantauriello.covidtracker.databinding.FragmentGlobalStatsBinding
import io.github.fabiantauriello.covidtracker.model.SummaryStats
import io.github.fabiantauriello.covidtracker.viewmodel.CountryStatsViewModel
import io.github.fabiantauriello.covidtracker.viewmodel.CountryStatsViewModelFactory
import io.github.fabiantauriello.covidtracker.viewmodel.GlobalStatsViewModel
import kotlinx.android.synthetic.main.fragment_summary_stats.*

class CountryStatsFragment : Fragment() {

    private val LOG_TAG = CountryStatsFragment::class.simpleName

    private val args: CountryStatsFragmentArgs by navArgs()
    private lateinit var menuItemRefresh: MenuItem // initialized in onCreateOptionsMenu
    private lateinit var countryStatsViewModel: CountryStatsViewModel

    private lateinit var binding: FragmentCountryStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView called with ${args.countryName}")

        // tell activity that this fragment has menu options it wants to add
        setHasOptionsMenu(true)

        val countrySelected = args.countryName

        // get country selected from previous screen and set it to be the app bar title
        (activity as AppCompatActivity).supportActionBar?.title = countrySelected

        countryStatsViewModel =
            ViewModelProvider(this, CountryStatsViewModelFactory(args.countryName)).get(
                CountryStatsViewModel::class.java
            )

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_country_stats, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = countryStatsViewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenu called")
        inflater.inflate(R.menu.refresh_menu, menu)
        menuItemRefresh = menu.findItem(R.id.action_refresh)
        configureLiveDataObserver()
    }

    private fun configureLiveDataObserver() {
        countryStatsViewModel.getCountryLiveData()
            .observe(requireActivity(), Observer { countryStats ->
                if (countryStats == null) {
                    showAPIError()
                }
                stopProgressBar()
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                startProgressBar()
                countryStatsViewModel.fetchCountrySummary()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun stopProgressBar() {
        menuItemRefresh.actionView = null
    }

    private fun startProgressBar() {
        menuItemRefresh.actionView = ProgressBar(activity)
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.title =
            getString(R.string.fragment_country_list_heading)
        super.onDetach()
    }

    private fun showAPIError() {
        Toast.makeText(
            activity?.applicationContext,
            getString(R.string.data_retrieval_error_msg),
            Toast.LENGTH_LONG
        ).show()
    }

}