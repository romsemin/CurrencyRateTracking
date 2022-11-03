package com.example.currencyratetracking.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyratetracking.datamodels.FavouriteRateDB

@Dao
interface FavouriteRatesDao {

    @Query("SELECT * FROM favourite_rates_table")
    fun getFavouriteRates(): List<FavouriteRateDB>

    @Query("DELETE FROM favourite_rates_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM favourite_rates_table WHERE code LIKE '%' || :code || '%' ")
    fun filterByCode(code: String): List<FavouriteRateDB>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code ASC")
    fun sortByCodeAsc(): List<FavouriteRateDB>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code DESC")
    fun sortByCodeDesc(): List<FavouriteRateDB>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate ASC")
    fun sortByRateAsc(): List<FavouriteRateDB>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate DESC")
    fun sortByRateDesc(): List<FavouriteRateDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteRateDB: FavouriteRateDB)

}