package com.example.weatherscope.network

import com.example.weatherscope.model.pojos.WeatherResponse
import retrofit2.Response

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double, lang: String, units:String): WeatherResponse
}