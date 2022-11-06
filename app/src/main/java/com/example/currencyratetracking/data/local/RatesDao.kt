package com.example.currencyratetracking.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyratetracking.datamodel.Rate

@Dao
interface RatesDao {

    @Query("SELECT * FROM favourite_rates_table")
    fun getAll(): List<Rate>

    @Query("DELETE FROM favourite_rates_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM favourite_rates_table WHERE code LIKE '%' || :code || '%' ")
    fun getByCode(code: String): List<Rate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code ASC")
    fun sortByCodeAsc(): List<Rate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY code DESC")
    fun sortByCodeDesc(): List<Rate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate ASC")
    fun sortByRateAsc(): List<Rate>

    @Query("SELECT * FROM favourite_rates_table ORDER BY rate DESC")
    fun sortByRateDesc(): List<Rate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favouriteRateDB: Rate)
}