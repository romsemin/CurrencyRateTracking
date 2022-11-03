package com.example.currencyratetracking.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyratetracking.dao.FavouriteRate
import com.example.currencyratetracking.dao.FavouriteRatesDao
import com.example.currencyratetracking.datamodels.RatesApiResponse
import com.example.currencyratetracking.utils.NetworkManager
import com.example.currencyratetracking.utils.SortOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val favouriteRatesDao: FavouriteRatesDao
) {
    private var favouriteRates: MutableLiveData<List<FavouriteRate>> = MutableLiveData()

    @Inject
    lateinit var networkManager: NetworkManager

    fun getFavouriteRates(): LiveData<List<FavouriteRate>> = favouriteRates

    suspend fun getBaseRates(base: String): Flow<RatesApiResponse> = flow {
        emit(networkManager.ratesService.getBaseRates(base))
    }.flowOn(Dispatchers.IO)

    suspend fun insert(favouriteRate: FavouriteRate) {
        return favouriteRatesDao.insert(favouriteRate)
    }

    suspend fun sortBy(sortOption: SortOption) {
        withContext(Dispatchers.IO) {
            when (sortOption) {
                SortOption.BY_RATE_ASC -> favouriteRates.postValue(favouriteRatesDao.sortByRateAsc())
                SortOption.BY_RATE_DESC -> favouriteRates.postValue(favouriteRatesDao.sortByCodeAsc())
                SortOption.BY_CODE_ASC -> favouriteRates.postValue(favouriteRatesDao.sortByCodeAsc())
                SortOption.BY_CODE_DESC -> favouriteRates.postValue(favouriteRatesDao.sortByCodeDesc())
                else -> favouriteRates.postValue(favouriteRatesDao.getAll())
            }
        }
    }

    suspend fun filterByCode(code: String) {
        withContext(Dispatchers.IO) {
            favouriteRates.postValue(favouriteRatesDao.filterByCode(code))
        }
    }
}