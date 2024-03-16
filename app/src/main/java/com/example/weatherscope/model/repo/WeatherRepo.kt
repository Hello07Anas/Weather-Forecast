package com.example.weatherscope.model.repo

import com.example.weatherscope.model.pojos.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    // From Network
    fun getAllMoviews(): Flow<WeatherResponse>

    // From Database TODO <<<<<>>>>>

}