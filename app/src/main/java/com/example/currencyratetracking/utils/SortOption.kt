package com.example.currencyratetracking.utils

import android.content.Context
import com.example.currencyratetracking.R

enum class SortOption {
    NONE,
    BY_RATE_ASC,
    BY_RATE_DESC,
    BY_CODE_ASC,
    BY_CODE_DESC;

    companion object {

        fun getSortOptions(context: Context): ArrayList<String> {
            val sortOptions: ArrayList<String> = ArrayList()
            sortOptions.add(context.getString(R.string.none))
            sortOptions.add(context.getString(R.string.by_rate))
            sortOptions.add(context.getString(R.string.by_rate_descending))
            sortOptions.add(context.getString(R.string.by_code))
            sortOptions.add(context.getString(R.string.by_code_descending))
            return sortOptions
        }

        fun getSortOption(id: Int): SortOption {
            return when (id) {
                1 -> BY_RATE_ASC
                2 -> BY_RATE_DESC
                3 -> BY_CODE_ASC
                4 -> BY_CODE_DESC
                else -> NONE
            }
        }
    }
}