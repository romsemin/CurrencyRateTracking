package com.example.currencyratetracking.utils

import android.content.Context
import com.example.currencyratetracking.R

enum class SortOption {
    BY_RATE_ASC,
    BY_RATE_DESC,
    BY_CODE_ASC,
    BY_CODE_DESC;

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
            return when (id) {
                0 -> BY_CODE_ASC
                1 -> BY_CODE_DESC
                2 -> BY_RATE_ASC
                3 -> BY_RATE_DESC
                else -> BY_CODE_ASC
            }
        }
    }
}