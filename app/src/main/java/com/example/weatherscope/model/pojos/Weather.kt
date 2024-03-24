package com.example.weatherscope.model.pojos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_response")
data class Weather(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "state")
    val main: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")

    val icon: String
)