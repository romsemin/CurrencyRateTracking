package com.example.currencyratetracking.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyratetracking.R
import com.example.currencyratetracking.datamodels.FavouriteRateDB
import com.example.currencyratetracking.databinding.RatesItemBinding

class FavouriteRatesAdapter : ListAdapter<FavouriteRateDB, FavouriteRatesAdapter.RatesViewHolder>(
    RatesDiffUtil
) {
    private var ratesList: MutableList<FavouriteRateDB> = mutableListOf()

    companion object RatesDiffUtil : DiffUtil.ItemCallback<FavouriteRateDB>() {
        override fun areItemsTheSame(oldItem: FavouriteRateDB, newItem: FavouriteRateDB): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavouriteRateDB, newItem: FavouriteRateDB): Boolean {
            return oldItem.code == newItem.code
        }
    }

    fun setData(items: List<FavouriteRateDB>) {
        ratesList.clear()
        ratesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(

        parent: ViewGroup,
        viewType: Int
    ): RatesViewHolder {
        val binding = RatesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val item = ratesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    inner class RatesViewHolder(private val binding: RatesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteRateDB) {
            binding.ratesItemCode.text = item.code
            binding.ratesItemRate.text = item.rate.toString()
            binding.ratesItemIcon.setImageResource(R.drawable.ic_baseline_star_rate_24)
        }
    }
}