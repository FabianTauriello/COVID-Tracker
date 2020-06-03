package io.github.fabiantauriello.covidtracker.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import io.github.fabiantauriello.covidtracker.R
import kotlinx.android.synthetic.main.country_list_item.view.*
import kotlin.collections.ArrayList

class CountryListAdapter(
    private val onCountryListener: OnCountryListener,
    private val countryNamesFiltered: ArrayList<String>, // filtered list
    private var countryNamesFull: Array<String> = countryNamesFiltered.toTypedArray() // full list
) : RecyclerView.Adapter<CountryListAdapter.MyViewHolder>(), Filterable {

    inner class MyViewHolder(itemView: View, val onCountryListener: OnCountryListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        fun bind(country: String) {
            itemView.tv_country_name.text = country
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // check if user is selecting country from filtered list (countryNames) or full list (countryNamesFull)
            val countrySelected =
                if (countryNamesFiltered.count() > 0) countryNamesFiltered[adapterPosition] else countryNamesFull[adapterPosition]
            onCountryListener.onCountryClicked(countrySelected)
        }
    }

    interface OnCountryListener {
        fun onCountryClicked(country: String)
    }

    fun updateCountryList(newList: Array<String>) {
        countryNamesFiltered.clear()
        countryNamesFiltered.addAll(newList)
        countryNamesFull = newList
        notifyDataSetChanged()
    }

    // Anonymous inner classes
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(searchInput: CharSequence?): FilterResults {
                var filteredList: ArrayList<String> = ArrayList()
                if (searchInput.isNullOrEmpty()) {
                    filteredList.addAll(countryNamesFull)
                } else {
                    val filterPattern = searchInput.toString().toLowerCase().trim()
                    for (item in countryNamesFull) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList.toTypedArray()
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryNamesFiltered.clear()
                countryNamesFiltered.addAll(results?.values as ArrayList<String>)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.country_list_item, parent, false)
        return MyViewHolder(view, onCountryListener)
    }

    override fun getItemCount(): Int = countryNamesFiltered.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(countryNamesFiltered[position])
    }
}