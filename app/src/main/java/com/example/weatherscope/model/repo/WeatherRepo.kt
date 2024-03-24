package com.example.weatherscope.model.repo

import com.example.weatherscope.model.pojos.Weather
import com.example.weatherscope.model.pojos.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepo {
    // From Network
    fun getWeather(): Flow<WeatherResponse>

    // From Database TODO <<<<<>>>>>

    fun getStoredWeather(): Flow<WeatherResponse>
    suspend fun insertWeather(weather: WeatherResponse)
    suspend fun deleteWeather(weather: WeatherResponse)
}