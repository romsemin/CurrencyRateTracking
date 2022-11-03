package com.example.currencyratetracking.utils

import com.example.currencyratetracking.datamodels.RatesApiResponse

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data: RatesApiResponse) : ApiState()
    object Empty : ApiState()
}