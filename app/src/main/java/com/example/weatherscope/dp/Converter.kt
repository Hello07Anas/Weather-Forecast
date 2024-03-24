package com.example.weatherscope.dp

import androidx.room.TypeConverter
import com.example.weatherscope.model.pojos.WeatherResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter { // for daily list
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<WeatherResponse.DailyForecast> {
        val listType = object : TypeToken<List<WeatherResponse.DailyForecast>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<WeatherResponse.DailyForecast>): String {
        return gson.toJson(list)
    }
}

class HourlyForecastConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJson(json: String): List<WeatherResponse.HourlyForecast> {
        val listType = object : TypeToken<List<WeatherResponse.HourlyForecast>>() {}.type
        return gson.fromJson(json, listType)
    }

    @TypeConverter
    fun toJson(list: List<WeatherResponse.HourlyForecast>): String {
        return gson.toJson(list)
    }
}