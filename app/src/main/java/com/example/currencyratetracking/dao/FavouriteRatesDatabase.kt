package com.example.currencyratetracking.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouriteRate::class],
    version = 1
)

abstract class FavouriteRatesDatabase : RoomDatabase() {

    abstract fun favouriteRatesDao(): FavouriteRatesDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteRatesDatabase? = null

        fun getDatabase(
            context: Context
        ): FavouriteRatesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteRatesDatabase::class.java,
                    "favourite_rates_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}