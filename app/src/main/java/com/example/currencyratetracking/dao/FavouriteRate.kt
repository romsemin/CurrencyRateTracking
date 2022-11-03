package com.example.currencyratetracking.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_rates_table")

data class FavouriteRate(
    @PrimaryKey
    @ColumnInfo(name = "code") val code: String,

    @ColumnInfo(name = "rate") val rate: Double
)


