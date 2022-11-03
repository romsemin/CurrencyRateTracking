package com.example.currencyratetracking.utils

import com.example.currencyratetracking.datamodels.RateApiResponse

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data: RateApiResponse) : ApiState()
    object Empty : ApiState()
}