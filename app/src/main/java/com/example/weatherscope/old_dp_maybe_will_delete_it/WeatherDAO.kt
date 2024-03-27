//package com.example.weatherscope.old_dp_maybe_will_delete_it
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.weatherscope.model.pojos.WeatherResponse
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface WeatherDAO {
//    @Query("SELECT * FROM weather_response")
//    fun getWeather(): Flow<WeatherResponse>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(weather: WeatherResponse)
//
//    @Delete
//    fun delete(weather: WeatherResponse)
//
//}
