package com.example.currencyratetracking.dao

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideFavRatesDao(favouriteRatesDatabase: FavouriteRatesDatabase): FavouriteRatesDao {
        return favouriteRatesDatabase.favouriteRatesDao()
    }

    @Provides
    @Singleton
    fun provideFavRatesDatabase(@ApplicationContext appContext: Context): FavouriteRatesDatabase {
        return FavouriteRatesDatabase.getDatabase(appContext)

    }
}