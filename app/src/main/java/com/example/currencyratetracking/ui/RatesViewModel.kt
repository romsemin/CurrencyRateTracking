package com.example.currencyratetracking.ui

import androidx.lifecycle.*
import com.example.currencyratetracking.datamodels.FavouriteRateDB
import com.example.currencyratetracking.datamodels.Rate
import com.example.currencyratetracking.util.ApiState
import com.example.currencyratetracking.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatesViewModel @Inject constructor(
    private val ratesRepository: RatesRepository,
) : ViewModel() {

    private val scope: CoroutineScope = viewModelScope
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    val ratesStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    private var _ratesLiveData: MutableLiveData<List<Rate>> = MutableLiveData()
    val ratesLiveData: MutableLiveData<List<Rate>> = _ratesLiveData

    val ratesLiveDataDB: LiveData<List<FavouriteRateDB>> = ratesRepository.getFavouriteRates()

    fun getRates(base: String) {
        scope.launch(dispatcher) {
            ratesStateFlow.value = ApiState.Loading
            ratesRepository.getRates(base)
                .catch { e -> ratesStateFlow.value = ApiState.Failure(e) }
                .collect { ratesStateFlow.value = ApiState.Success(it) }
        }
    }

    fun insert(favouriteRateDB: FavouriteRateDB) {
        scope.launch(dispatcher) {
            ratesRepository.insert(favouriteRateDB)
        }
    }

    fun getFavouriteRates(sortOption: SortOption) {
        scope.launch(dispatcher) {
            ratesRepository.sortBy(sortOption)
        }
    }

    fun getFilteredFavouriteRates(code: String) {
        scope.launch(dispatcher) {
            ratesRepository.filterByCode(code)
        }
    }
}

