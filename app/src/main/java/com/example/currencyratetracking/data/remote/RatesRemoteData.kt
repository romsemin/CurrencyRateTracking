package com.example.currencyratetracking.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRemoteData @Inject constructor(
    private val ratesService: RatesService
) {
    suspend fun getRates(base: String) = ratesService.getRates(base)
}

