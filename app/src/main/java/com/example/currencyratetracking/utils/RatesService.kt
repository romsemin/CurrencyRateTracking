package com.example.currencyratetracking.utils

import retrofit2.http.GET
import com.example.currencyratetracking.datamodels.RatesApiResponse
import retrofit2.http.Query


interface RatesService {

    @GET("latest")
    suspend fun getBaseRates(
        @Query("base") base: String
    ): RatesApiResponse

}