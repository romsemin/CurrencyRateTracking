package com.example.currencyratetracking.data.remote

import retrofit2.http.GET
import com.example.currencyratetracking.datamodels.RateApiResponse
import retrofit2.http.Query

interface RatesService {

    @GET("latest")
    suspend fun getRates(
        @Query("base") base: String
    ): RateApiResponse
}