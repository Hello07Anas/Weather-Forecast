package com.example.weatherscope.model.pojos

data class WeatherResponse(
    val daily: List<DailyForecast>,
    val hourly: List<HourlyForecast>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
    // TODO have to add >>> Alert
) {
    data class DailyForecast(
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val moonrise: Long,
        val moonset: Long,
        val moon_phase: Double,
        val summary: String,
        val temp: Temperature,
        val feels_like: FeelsLike,
        val pressure: Int,
        val humidity: Int,
        val dew_point: Double,
        val wind_speed: Double,
        val wind_deg: Int,
        val wind_gust: Double,
        val weather: List<Weather>,
        val clouds: Int,
        val pop: Float,
        val uvi: Double
    )

    data class HourlyForecast(
        val dt: Long,
        val temp: Double,
        val feels_like: Double,
        val pressure: Int,
        val humidity: Int,
        val dew_point: Double,
        val uvi: Double,
        val clouds: Int,
        val visibility: Int,
        val wind_speed: Double,
        val wind_deg: Int,
        val wind_gust: Double,
        val weather: List<Weather>,
        val pop: Float
    )
}