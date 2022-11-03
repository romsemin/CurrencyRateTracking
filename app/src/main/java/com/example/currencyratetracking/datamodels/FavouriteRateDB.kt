package com.example.currencyratetracking.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_rates_table")
data class FavouriteRateDB(
    @PrimaryKey
    val code: String,
    val rate: Double
)