package io.github.fabiantauriello.covidtracker.view

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.fabiantauriello.covidtracker.R
import kotlinx.android.synthetic.main.fragment_country_list.*


class CountryListFragment : Fragment(), CountryListAdapter.OnCountryListener {

    private val LOG_TAG = CountryListFragment::class.simpleName

    private val args: CountryListFragmentArgs by navArgs()
    private val adapter = CountryListAdapter(this, ArrayList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_country_list_heading)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get country list from previous screen and give it to adapter
        val countryList: Array<String> = args.countryList
        adapter.updateCountryList(countryList)

        configureRecyclerView()
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.fragment_global_stats_heading)
        super.onDetach()
    }

    private fun configureRecyclerView() {
        rv_countries.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        rv_countries.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.country_list_menu, menu)
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
                toggleKeyboard(false, searchView)
            }

            override fun onViewAttachedToWindow(v: View?) {
                searchView.requestFocus()
                toggleKeyboard(true, searchView)
            }
        })
    }

    private fun toggleKeyboard(show: Boolean, view: View) {
        val imm =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (show) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.RESULT_HIDDEN)
        } else {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCountryClicked(country: String) {
        // CountryListFragmentDirections generated from safe args plugin
        val action =
            CountryListFragmentDirections.actionFragmentCountryListToFragmentCountryStats(country)
        findNavController().navigate(action)
    }

}
