package com.example.currencyratetracking.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyratetracking.R
import com.example.currencyratetracking.datamodels.Rate
import com.example.currencyratetracking.databinding.RateItemBinding

class PopularRatesAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Rate, PopularRatesAdapter.RatesViewHolder>(RatesDiffUtil) {
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
        val binding = RateItemBinding.inflate(
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

    inner class RatesViewHolder(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rate) {
            binding.rateItemCode.text = item.code
            binding.rateItemRate.text = item.rate.toString()
            binding.rateItemIcon.setImageResource(R.drawable.ic_baseline_star_outline)
        }
    }

    class OnClickListener(val clickListener: (rate: Rate) -> Unit) {
        fun onClick(rate: Rate) = clickListener(rate)
    }
}