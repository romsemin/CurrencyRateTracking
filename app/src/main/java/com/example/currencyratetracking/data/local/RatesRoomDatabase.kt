package com.example.currencyratetracking.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyratetracking.datamodel.Rate

@Database(
    entities = [Rate::class],
    version = 1
)

abstract class RatesRoomDatabase : RoomDatabase() {

    abstract fun favouriteRatesDao(): RatesDao

    companion object {
        @Volatile
        private var INSTANCE: RatesRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): RatesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RatesRoomDatabase::class.java,
                    "favourite_rates_room_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}