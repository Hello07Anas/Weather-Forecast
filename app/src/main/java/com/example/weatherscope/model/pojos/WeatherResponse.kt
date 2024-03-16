package com.example.weatherscope.model.pojos

data class WeatherResponse(
    val daily: List<DailyForecast>,
    val hourly: List<HourlyForecast>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
    // TODO have to add >>> Alert
)