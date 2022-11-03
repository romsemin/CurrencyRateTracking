package com.example.currencyratetracking.datamodels

import com.google.gson.annotations.SerializedName

data class RateApiResponse(
    @SerializedName("rates")
    val rates: Map<String, Double>,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String
)
