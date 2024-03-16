package com.example.weatherscope.network

import com.example.weatherscope.model.pojos.WeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double): WeatherResponse
}