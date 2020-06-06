package io.github.fabiantauriello.covidtracker.view

import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.model.SummaryStats
import io.github.fabiantauriello.covidtracker.presenter.CountryStatsContract
import io.github.fabiantauriello.covidtracker.presenter.CountryStatsPresenter
import kotlinx.android.synthetic.main.fragment_summary_stats.*

class CountryStatsFragment : Fragment(), CountryStatsContract.View {

    private val LOG_TAG = CountryStatsFragment::class.simpleName

    private val args: CountryStatsFragmentArgs by navArgs()
    val presenter = CountryStatsPresenter()
    private lateinit var menuItemRefresh: MenuItem // initialized in onCreateOptionsMenu
    private lateinit var countrySelected: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // tell activity that this fragment has menu options it wants to add
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get country selected from previous screen and set it to be the app bar title
        countrySelected = args.countryName
        (activity as AppCompatActivity).supportActionBar?.title = countrySelected

        presenter.setView(this)
        presenter.fetchCountrySummary(requireActivity().applicationContext, countrySelected)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        menuItemRefresh = menu.findItem(R.id.action_refresh)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                item.actionView = ProgressBar(activity)
                presenter.fetchCountrySummary(requireActivity().applicationContext, countrySelected)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun stopProgressBar() {
        menuItemRefresh.actionView = null
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_country_list_heading)
        super.onDetach()
    }

    override fun updateSummaryStats(summary: SummaryStats?) {
        tv_value_new_cases.text = summary?.newCases
        tv_value_total_cases.text = summary?.totalCases
        tv_value_new_deaths.text = summary?.newDeaths
        tv_value_total_deaths.text = summary?.totalDeaths
        tv_value_new_recovered.text = summary?.newRecovered
        tv_value_total_recovered.text = summary?.totalRecovered
    }

    override fun showAPIError() {
        Toast.makeText(
            activity?.applicationContext,
            getString(R.string.data_retrieval_error_msg),
            Toast.LENGTH_LONG
        ).show()
    }

}