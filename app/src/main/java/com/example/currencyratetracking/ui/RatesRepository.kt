package com.example.currencyratetracking.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyratetracking.datamodel.Rate
import com.example.currencyratetracking.data.local.RatesDao
import com.example.currencyratetracking.datamodel.RatesApiResponse
import com.example.currencyratetracking.data.remote.RatesRemoteData
import com.example.currencyratetracking.util.SortOption
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
    private val ratesDao: RatesDao
) {
    private var ratesDB: MutableLiveData<List<Rate>> = MutableLiveData()

    fun getRatesDB(): LiveData<List<Rate>> = ratesDB

    suspend fun getRates(base: String): Flow<RatesApiResponse> = flow {
        emit(ratesRemoteData.getRates(base))
    }.flowOn(Dispatchers.IO)

    suspend fun insert(rate: Rate) {
        return ratesDao.insert(rate)
    }

    suspend fun sortBy(sortOption: SortOption) {
        withContext(Dispatchers.IO) {
            when (sortOption) {
                SortOption.BY_RATE_ASC -> ratesDB.postValue(ratesDao.sortByRateAsc())
                SortOption.BY_RATE_DESC -> ratesDB.postValue(ratesDao.sortByRateDesc())
                SortOption.BY_CODE_ASC -> ratesDB.postValue(ratesDao.sortByCodeAsc())
                SortOption.BY_CODE_DESC -> ratesDB.postValue(ratesDao.sortByCodeDesc())
            }
        }
    }

    suspend fun getRatesDBByCode(code: String) {
        withContext(Dispatchers.IO) {
            ratesDB.postValue(ratesDao.getByCode(code))
        }
    }
}