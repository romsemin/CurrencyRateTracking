package com.example.currencyratetracking.util

import com.example.currencyratetracking.datamodel.RatesApiResponse

sealed class ApiState{
    object Empty : ApiState()
    object Loading : ApiState()
    class Failure(val message: Throwable) : ApiState()
    class Success(val data: RatesApiResponse) : ApiState()
}