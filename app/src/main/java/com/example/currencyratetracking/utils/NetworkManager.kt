package com.example.currencyratetracking.utils

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkManager @Inject constructor() {

    val ratesService: RatesService by lazy {
        Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory
                .create(GsonBuilder()
                    .create()
                )
            )
            .build()
            .create(RatesService::class.java)
    }
}