package com.example.weatherscope.network

import android.util.Log
import com.example.weatherscope.model.pojos.WeatherResponse

private const val TAG = "WeatherRemoteDataSourceIMP"
class WeatherRemoteDataSourceIMP: WeatherRemoteDataSource {

    private val weatherService : WeatherService by lazy {
        RetrofitHelper.getInstance().create(WeatherService::class.java)
    }

    override suspend fun getWeather(lat: Double, lon: Double): WeatherResponse {
        val part = arrayOf("current", "minutely", "hourly", "daily", "alerts")
        val apiKey = "51480a89243b1c0fde77f3cf9c774157"


        try {
            return weatherService.getWeather(lat, lon, part[1], apiKey)
        } catch (e: Exception) {
            Log.i(TAG, "Error fetching weather data: ${e.message}")
            throw e
        }
    }
}