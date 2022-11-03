package com.example.currencyratetracking.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyratetracking.R
import com.example.currencyratetracking.datamodels.Rate
import com.example.currencyratetracking.databinding.RatesItemBinding

class PopularRatesAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<Rate, PopularRatesAdapter.RatesViewHolder>(RatesDiffUtil) {
    private var ratesList : MutableList<Rate> = mutableListOf()

    companion object RatesDiffUtil : DiffUtil.ItemCallback<Rate>() {
        override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean {
            return oldItem.code == newItem.code
        }
    }

    fun setData(items: List<Rate>) {
        ratesList.clear()
        ratesList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val binding = RatesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val item = ratesList[position]
        holder.itemView.setOnClickListener{
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    inner class RatesViewHolder(private val binding: RatesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rate) {
            binding.ratesItemCode.text = item.code
            binding.ratesItemRate.text = item.rate.toString()
            binding.ratesItemIcon.setImageResource(R.drawable.ic_baseline_star_outline_24)
        }
    }

    class OnClickListener(
        val clickListener: (rate: Rate) -> Unit
    ) {
        fun onClick(rate: Rate) = clickListener(rate)
    }
}