package io.github.fabiantauriello.covidtracker.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.viewmodel.CountryListViewModel
import kotlinx.android.synthetic.main.fragment_country_list.*
import kotlin.math.log

class CountryListFragment : Fragment(), OpenCountryStatsNavigator {

    private val LOG_TAG = this::class.simpleName

    private lateinit var menuItemRefresh: MenuItem

    private lateinit var countryListViewModel: CountryListViewModel
    private lateinit var adapter: CountryListAdapter

    private var bool = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView called")

        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_country_list_heading)

        countryListViewModel = ViewModelProvider(requireActivity()).get(CountryListViewModel::class.java)
        countryListViewModel.navigator = this

        adapter = CountryListAdapter(countryListViewModel, ArrayList())

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureLiveDataObserver()
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_global_stats_heading)
        super.onDetach()
    }

    private fun configureLiveDataObserver() {
        countryListViewModel.getCountryListLiveData()
            .observe(viewLifecycleOwner, Observer { countryList ->
                if (countryList == null) {
                    showAPIError()
                } else {
                    adapter.updateData(countryList)
                }
                stopProgressBar()
            })
    }

    private fun configureRecyclerView() {
        rv_countries.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rv_countries.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.country_list_menu, menu)

        menuItemRefresh = menu.findItem(R.id.action_refresh)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.isIconifiedByDefault = false
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val adapter = rv_countries.adapter as CountryListAdapter
                adapter.filter.filter(newText)
                return false
            }
        })
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                Log.d(LOG_TAG, "onViewDetachedFromWindow: ")
                toggleKeyboard(false, searchView)
            }

            override fun onViewAttachedToWindow(v: View?) {
                Log.d(LOG_TAG, "onViewAttachedToWindow: ")
                searchView.requestFocus()
                toggleKeyboard(true, searchView)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                startProgressBar()
                countryListViewModel.fetchCountryList()
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

    private fun toggleKeyboard(show: Boolean, view: View) {
        activity?.let {
            val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            if (show) {
                imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.RESULT_HIDDEN)
                bool = true
            } else {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                bool = false
            }
        }
    }

    override fun navigate(view: View) {
        val countrySelected = (view as TextView).text.toString()
        val action = CountryListFragmentDirections.actionCountryListFragToCountryFrag(countrySelected)
        view.findNavController().navigate(action)
    }

    private fun showAPIError() {
        Toast.makeText(
            activity?.applicationContext,
            getString(R.string.data_retrieval_error_msg),
            Toast.LENGTH_LONG
        ).show()
    }

}
