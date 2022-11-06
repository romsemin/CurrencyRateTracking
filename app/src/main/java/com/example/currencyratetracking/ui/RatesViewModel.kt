package com.example.currencyratetracking.ui

import androidx.lifecycle.*
import com.example.currencyratetracking.datamodel.Rate
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

    val ratesDBLiveData: LiveData<List<Rate>> = ratesRepository.getRatesDB()

    fun getRates(base: String) {
        scope.launch(dispatcher) {
            ratesStateFlow.value = ApiState.Loading
            ratesRepository.getRates(base)
                .catch { e -> ratesStateFlow.value = ApiState.Failure(e) }
                .collect { ratesStateFlow.value = ApiState.Success(it) }
        }
    }

    fun insert(rate: Rate) {
        scope.launch(dispatcher) {
            ratesRepository.insert(rate)
        }
    }

    fun getRatesDB(sortOption: SortOption) {
        scope.launch(dispatcher) {
            ratesRepository.sortBy(sortOption)
        }
    }

    fun getRatesDBByCode(code: String) {
        scope.launch(dispatcher) {
            ratesRepository.getRatesDBByCode(code)
        }
    }
}

