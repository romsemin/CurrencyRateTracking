package com.example.currencyratetracking.util

import android.content.Context
import com.example.currencyratetracking.R

enum class SortOption {
    BY_CODE_ASC,
    BY_CODE_DESC,
    BY_RATE_ASC,
    BY_RATE_DESC;

    companion object {

        fun getSortOptions(context: Context): ArrayList<String> {
            val sortOptions: ArrayList<String> = ArrayList()
            sortOptions.add(context.getString(R.string.by_code_ascending))
            sortOptions.add(context.getString(R.string.by_code_descending))
            sortOptions.add(context.getString(R.string.by_rate_ascending))
            sortOptions.add(context.getString(R.string.by_rate_descending))
            return sortOptions
        }

        fun getSortOption(id: Int): SortOption {
            return values()[id]
        }
    }
}