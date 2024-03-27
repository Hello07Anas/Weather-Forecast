//package com.example.weatherscope.old_dp_maybe_will_delete_it
//
//import com.example.weatherscope.model.pojos.WeatherResponse
//import kotlinx.coroutines.flow.Flow
//
//interface WeatherLocalDataSource {
//
//    suspend fun insertWeather(weather: WeatherResponse)
//    suspend fun delteWeather(weather: WeatherResponse)
//    fun getWeather(): Flow<WeatherResponse>
//
//}