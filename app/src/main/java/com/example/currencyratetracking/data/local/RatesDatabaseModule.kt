package com.example.currencyratetracking.data.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RatesDatabaseModule {

    @Provides
    fun provideFavouriteRatesDao(ratesRoomDatabase: RatesRoomDatabase): RatesDao =
        ratesRoomDatabase.favouriteRatesDao()

    @Provides
    @Singleton
    fun provideFavouriteRatesDatabase(@ApplicationContext appContext: Context): RatesRoomDatabase =
        RatesRoomDatabase.getDatabase(appContext)
}