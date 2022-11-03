package com.example.currencyratetracking.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRatesDao {

    @Query("SELECT * FROM favourite_rates_table")
    fun getAll(): List<FavouriteRate>

    @Query("DELETE FROM favourite_rates_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM favourite_rates_table WHERE code LIKE '%' || :code || '%' ")
    fun filterByCode(code: String): List<FavouriteRate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code ASC")
    fun sortByCodeAsc(): List<FavouriteRate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code DESC")
    fun sortByCodeDesc(): List<FavouriteRate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate ASC")
    fun sortByRateAsc(): List<FavouriteRate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate DESC")
    fun sortByRateDesc(): List<FavouriteRate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteRate: FavouriteRate)

}