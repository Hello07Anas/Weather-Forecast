package com.example.weatherscope.util

import com.example.weatherscope.model.pojos.WeatherResponse

sealed class ApiState { // TODO learn about sealed class and this class reason
    object Loading: ApiState()
    class Success(val data: WeatherResponse) : ApiState()
    class Error(val message: Throwable) : ApiState()
}

// this the first step in implementing coroutine on Api res