package com.example.currencyratetracking.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyratetracking.datamodels.FavouriteRateDB

@Database(
    entities = [FavouriteRateDB::class],
    version = 1
)

abstract class FavouriteRatesRoomDatabase : RoomDatabase() {

    abstract fun favouriteRatesDao(): FavouriteRatesDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteRatesRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): FavouriteRatesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteRatesRoomDatabase::class.java,
                    "favourite_rates_room_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}