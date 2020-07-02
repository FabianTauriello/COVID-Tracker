package io.github.fabiantauriello.covidtracker.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.databinding.CountryListItemBinding
import io.github.fabiantauriello.covidtracker.viewmodel.CountryListViewModel
import kotlin.collections.ArrayList

class CountryListAdapter(
    private val viewModel: CountryListViewModel,
    private var countryNamesFiltered: ArrayList<String>, // list that will be shown to user
    private var countryNames: ArrayList<String> = countryNamesFiltered.clone() as ArrayList<String>
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>(), Filterable {

    private val LOG_TAG = this::class.simpleName

    inner class MyViewHolder(
        val binding: CountryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    }

    // creates view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate<CountryListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.country_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    // binds data to view holder
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.countryName = countryNamesFiltered[position]
        holder.binding.viewModel = viewModel
    }

    override fun getItemCount() = countryNamesFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(searchInput: CharSequence?): FilterResults {
                var filteredList: ArrayList<String> = ArrayList()
                if (searchInput.isNullOrEmpty()) {
                    filteredList.addAll(countryNames)
                } else {
                    val trimmedSearchText = searchInput.toString().toLowerCase().trim()
                    for (countryName in countryNames) {
                        if (countryName.toLowerCase().contains(trimmedSearchText)) {
                            filteredList.add(countryName)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList.toTypedArray()
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryNamesFiltered.clear()
                countryNamesFiltered.addAll(results?.values as Array<String>)
                notifyDataSetChanged()
            }
        }
    }

    fun updateData(newList: ArrayList<String>) {
        countryNamesFiltered.clear()
        countryNamesFiltered.addAll(newList)
        countryNames = newList
        notifyDataSetChanged()
    }

}