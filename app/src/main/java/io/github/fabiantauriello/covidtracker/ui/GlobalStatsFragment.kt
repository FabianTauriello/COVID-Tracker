package io.github.fabiantauriello.covidtracker.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.database.COVIDTrackerDatabase
import io.github.fabiantauriello.covidtracker.databinding.FragmentGlobalStatsBinding
import io.github.fabiantauriello.covidtracker.viewmodels.GlobalStatsViewModel

class GlobalStatsFragment : Fragment(), OpenCountryListNavigator {

    private val LOG_TAG = this::class.simpleName

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

    override fun navigate() {
        val action = GlobalStatsFragmentDirections.actionGlobalFragToCountryListFrag()
        findNavController().navigate(action)
    }

}
