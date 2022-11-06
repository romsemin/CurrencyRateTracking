package com.example.currencyratetracking.datamodel

data class RatesApiResponse(
    val rates: Map<String, Double>,
    val base: String,
    val date: String
)
