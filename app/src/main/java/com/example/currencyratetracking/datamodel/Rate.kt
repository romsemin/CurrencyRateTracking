package com.example.currencyratetracking.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_rates_table")
data class Rate(
    @PrimaryKey
    val code: String,
    val rate: Double
)