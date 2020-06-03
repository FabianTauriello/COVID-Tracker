package io.github.fabiantauriello.covidtracker.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.findNavController

import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.model.SummaryStats
import io.github.fabiantauriello.covidtracker.presenter.GlobalStatsContract
import io.github.fabiantauriello.covidtracker.presenter.GlobalStatsPresenter
import kotlinx.android.synthetic.main.fragment_global_stats.*
import kotlinx.android.synthetic.main.fragment_summary_stats.*

class GlobalStatsFragment : Fragment(), GlobalStatsContract.View {

    private val LOG_TAG = GlobalStatsFragment::class.simpleName

    private val presenter = GlobalStatsPresenter()
    private lateinit var menuItemRefresh: MenuItem // initialized in onCreateOptionsMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // tell activity that this fragment has menu options it wants to add
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_global_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setView(this)
        presenter.fetchSummaryPair(requireActivity().applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
        menuItemRefresh = menu.findItem(R.id.action_refresh)
    }

    // I should return true if I process the menu item and return super.onOptionsItemSelected(item) if I don't.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                item.actionView = ProgressBar(activity)
                presenter.fetchSummaryPair(requireActivity().applicationContext)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun stopProgressBar() {
        menuItemRefresh.actionView = null
    }

    override fun configureButton(countryList: Array<String>) {
        btn_country_stats.isEnabled = true
        btn_country_stats.setOnClickListener {
            val action =
                GlobalStatsFragmentDirections.actionFragmentWorldStatsToFragmentCountryList(
                    countryList
                )
            it.findNavController().navigate(action)
        }
    }

    override fun updateData(summary: SummaryStats?) {
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

        btn_country_stats.isEnabled = false
    }

}
