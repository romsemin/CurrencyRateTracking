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
object FavouriteRatesDatabaseModule {

    @Provides
    fun provideFavRatesDao(favouriteRatesRoomDatabase: FavouriteRatesRoomDatabase): FavouriteRatesDao {
        return favouriteRatesRoomDatabase.favouriteRatesDao()
    }

    @Provides
    @Singleton
    fun provideFavRatesDatabase(@ApplicationContext appContext: Context): FavouriteRatesRoomDatabase {
        return FavouriteRatesRoomDatabase.getDatabase(appContext)

    }
}