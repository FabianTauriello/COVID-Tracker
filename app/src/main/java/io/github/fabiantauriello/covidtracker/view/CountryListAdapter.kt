package io.github.fabiantauriello.covidtracker.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import io.github.fabiantauriello.covidtracker.R
import io.github.fabiantauriello.covidtracker.databinding.CountryListItemBinding
import io.github.fabiantauriello.covidtracker.viewmodel.CountryListViewModel
import kotlin.collections.ArrayList

class CountryListAdapter(
    private var countryNames: ArrayList<String>?,
    private val viewModel: CountryListViewModel
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
        Log.d(LOG_TAG, "onBindViewHolder called")
        countryNames?.let {
            holder.binding.countryName = countryNames?.get(position)
            holder.binding.viewModel = viewModel
        }
    }

    override fun getItemCount() = countryNames?.size ?: 0

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

    fun updateData(countryNames: ArrayList<String>?) {
        this.countryNames = countryNames // TODO replace data instead of copying reference
        notifyDataSetChanged()
    }



}