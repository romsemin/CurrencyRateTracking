package com.example.currencyratetracking.ui

import androidx.lifecycle.*
import com.example.currencyratetracking.dao.FavouriteRate
import com.example.currencyratetracking.datamodels.RatesApiResponse
import com.example.currencyratetracking.utils.ApiState
import com.example.currencyratetracking.utils.Const
import com.example.currencyratetracking.utils.SortOption
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

    private var _responseApiLiveData: MutableLiveData<RatesApiResponse> = MutableLiveData()
    val responseApiLiveData: MutableLiveData<RatesApiResponse> = _responseApiLiveData

    val favouriteRatesLiveData: LiveData<List<FavouriteRate>> = ratesRepository.getFavouriteRates()

    init {
        getBaseRates(Const.BASE_CURRENCY)
    }

    fun getBaseRates(base: String) {
        scope.launch(dispatcher) {
            ratesStateFlow.value = ApiState.Loading
            ratesRepository.getBaseRates(base)
                .catch { e -> ratesStateFlow.value = ApiState.Failure(e) }
                .collect { ratesStateFlow.value = ApiState.Success(it) }
        }
    }

    fun addToFavourite(favouriteRate: FavouriteRate) {
        scope.launch(dispatcher) {
            ratesRepository.insert(favouriteRate)
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

