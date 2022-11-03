package com.example.currencyratetracking.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatesRemoteDataModule {

    @Provides
    fun providesBaseUrl(): String = "https://api.exchangerate.host/"

    @Provides
    @Singleton
    fun providesRetrofit(BASE_URL: String): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesRatesService(retrofit: Retrofit): RatesService = retrofit.create(RatesService::class.java)

    @Provides
    @Singleton
    fun provideRatesRemoteData(ratesService: RatesService): RatesRemoteData = RatesRemoteData(ratesService)
}