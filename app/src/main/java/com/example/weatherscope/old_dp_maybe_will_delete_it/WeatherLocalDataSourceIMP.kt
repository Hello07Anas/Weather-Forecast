//package com.example.weatherscope.old_dp_maybe_will_delete_it
//
//import android.content.Context
//import com.example.weatherscope.model.pojos.WeatherResponse
//import kotlinx.coroutines.flow.Flow
//
//class WeatherLocalDataSourceIMP(context: Context) : WeatherLocalDataSource {
//
//    private val dao: WeatherDAO by lazy {
//        val dp: AppDB = AppDB.getInstance(context)
//        dp.weatherDao()
//    }
//    override suspend fun insertWeather(weather: WeatherResponse) {
//        dao.insert(weather)
//    }
//
//    override suspend fun delteWeather(weather: WeatherResponse) {
//        dao.delete(weather)
//    }
//
//    override fun getWeather(): Flow<WeatherResponse> {
//        return dao.getWeather()
//    }
//}