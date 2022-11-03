package com.example.currencyratetracking.ui.favourite

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyratetracking.dao.FavouriteRate
import com.example.currencyratetracking.databinding.RatesFavouriteItemBinding

class FavouriteRatesAdapter : ListAdapter<FavouriteRate, FavouriteRatesAdapter.RatesViewHolder>(
    RatesDiffUtil
) {
    private var favouriteRatesList: MutableList<FavouriteRate> = mutableListOf()

    companion object RatesDiffUtil : DiffUtil.ItemCallback<FavouriteRate>() {
        override fun areItemsTheSame(oldItem: FavouriteRate, newItem: FavouriteRate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavouriteRate, newItem: FavouriteRate): Boolean {
            return oldItem.code == newItem.code
        }
    }

    fun setData(items: List<FavouriteRate>) {
        favouriteRatesList.clear()
        favouriteRatesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(

        parent: ViewGroup,
        viewType: Int
    ): RatesViewHolder {
        val binding = RatesFavouriteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val item = favouriteRatesList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return favouriteRatesList.size
    }

    inner class RatesViewHolder(private val binding: RatesFavouriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavouriteRate) {
            binding.ratesFavouriteItemCode.text = item.code
            binding.ratesFavouriteItemRate.text = item.rate.toString()
        }
    }
}