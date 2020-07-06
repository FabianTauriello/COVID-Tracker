package io.github.fabiantauriello.covidtracker.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.databinding.FragmentGlobalStatsBinding
import io.github.fabiantauriello.covidtracker.viewmodel.GlobalStatsViewModel


class GlobalStatsFragment : Fragment(), OpenCountryListNavigator {

    private val LOG_TAG = this::class.simpleName

    private lateinit var menuItemRefresh: MenuItem
    private lateinit var globalStatsViewModel: GlobalStatsViewModel

    private lateinit var binding: FragmentGlobalStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView called")

        // tell activity that this fragment has menu options it wants to add
        setHasOptionsMenu(true)

        // using the activity as the owner means that the data will persist over the lifecycle of the activity. Therefore, the same ViewModel, with the
        // same data will be used if the user moves to one fragment, and returns to this one, and data will not be re-fetched.
        globalStatsViewModel =
            ViewModelProvider(requireActivity()).get(GlobalStatsViewModel::class.java)
        globalStatsViewModel.navigator = this

        // initialize binding
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_global_stats, container, false)

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the ViewModel
        binding.viewModel = globalStatsViewModel

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Log.d(LOG_TAG, "onCreateOptionsMenu called")
        inflater.inflate(R.menu.refresh_menu, menu)
        menuItemRefresh = menu.findItem(R.id.action_refresh)
        configureLiveDataObserver()
    }

    // I should return true if I process the menu item and return super.onOptionsItemSelected(item) if I don't.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                startProgressBar()
                globalStatsViewModel.fetchGlobalSummary()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // observe changes to monitor success of api call
    private fun configureLiveDataObserver() {
        globalStatsViewModel.getGlobalLiveData()
            .observe(requireActivity(), Observer { globalStats ->
                Log.d(LOG_TAG, "configureLiveDataObserver: ")
                if (globalStats == null) {
                    showAPIError()
                }
                stopProgressBar()
            })
    }

    private fun stopProgressBar() {
        menuItemRefresh.actionView = null
    }

    private fun startProgressBar() {
        menuItemRefresh.actionView = ProgressBar(activity)
    }

    private fun showAPIError() {
        Toast.makeText(
            activity?.applicationContext,
            getString(R.string.data_retrieval_error_msg),
            Toast.LENGTH_LONG
        ).show()
    }

    override fun navigate() {
        val action = GlobalStatsFragmentDirections.actionGlobalFragToCountryListFrag()
        findNavController().navigate(action)
    }

}
