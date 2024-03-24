package com.example.weatherscope.dp

import com.example.weatherscope.model.pojos.Weather
import com.example.weatherscope.model.pojos.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSource {

    suspend fun insertWeather(weather: WeatherResponse)
    suspend fun delteWeather(weather: WeatherResponse)
    fun getWeather(): Flow<WeatherResponse>

}