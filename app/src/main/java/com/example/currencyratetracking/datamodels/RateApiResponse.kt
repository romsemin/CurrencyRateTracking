package com.example.currencyratetracking.datamodels

data class RateApiResponse(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
)
