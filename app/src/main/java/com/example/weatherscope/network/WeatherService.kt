package com.example.weatherscope.network

import com.example.weatherscope.model.pojos.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    @GET("onecall")
    suspend fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String,
        @Query("units") units: String
        // TODO add lang and (unit >> انواع درجات الحرارة)
    ): WeatherResponse
}

/*
        Celsius is for metric units.
        Kelvin is an absolute temperature scale.
        Fahrenheit is for imperial units.
 */


