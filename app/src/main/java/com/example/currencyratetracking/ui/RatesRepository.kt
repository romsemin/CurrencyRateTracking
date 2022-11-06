package com.example.currencyratetracking.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyratetracking.datamodels.FavouriteRateDB
import com.example.currencyratetracking.data.local.FavouriteRatesDao
import com.example.currencyratetracking.datamodels.RateApiResponse
import com.example.currencyratetracking.data.remote.RatesRemoteData
import com.example.currencyratetracking.utils.SortOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RatesRepository @Inject constructor(
    private val ratesRemoteData: RatesRemoteData,
    private val favouriteRatesDao: FavouriteRatesDao
) {
    private var ratesDB: MutableLiveData<List<FavouriteRateDB>> = MutableLiveData()

    fun getFavouriteRates(): LiveData<List<FavouriteRateDB>> = ratesDB

    suspend fun getRates(base: String): Flow<RateApiResponse> = flow {
        emit(ratesRemoteData.getRates(base))
    }.flowOn(Dispatchers.IO)

    suspend fun insert(favouriteRateDB: FavouriteRateDB) {
        return favouriteRatesDao.insert(favouriteRateDB)
    }

    suspend fun sortBy(sortOption: SortOption) {
        withContext(Dispatchers.IO) {
            when (sortOption) {
                SortOption.BY_RATE_ASC -> ratesDB.postValue(favouriteRatesDao.sortByRateAsc())
                SortOption.BY_RATE_DESC -> ratesDB.postValue(favouriteRatesDao.sortByRateDesc())
                SortOption.BY_CODE_ASC -> ratesDB.postValue(favouriteRatesDao.sortByCodeAsc())
                SortOption.BY_CODE_DESC -> ratesDB.postValue(favouriteRatesDao.sortByCodeDesc())
            }
        }
    }

    suspend fun filterByCode(code: String) {
        withContext(Dispatchers.IO) {
            ratesDB.postValue(favouriteRatesDao.filterByCode(code))
        }
    }
}