package com.example.weatherscope.model.repo

//import com.example.weatherscope.old_dp_maybe_will_delete_it.WeatherLocalDataSource
import android.util.Log
import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.network.WeatherRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class WeatherRepoIMP(
    // TODO here will make ref form remote Data and local
    private var weatherReamoteDataSource: WeatherRemoteDataSource,
    //private var weatherLocalDataSource: WeatherLocalDataSource
) : WeatherRepo {

    companion object { // insted of static in java
        private var instance: WeatherRepoIMP? = null
        fun getInstance(
            weatherReamoteDataSource: WeatherRemoteDataSource,
            //weatherLocalDataSource: WeatherLocalDataSource
            // TODO here localData
        ): WeatherRepoIMP {
            return instance?: synchronized(this) {
                val temp = WeatherRepoIMP(
                    weatherReamoteDataSource
                    //weatherLocalDataSource
                )//, TODO adding local Data too
                instance = temp
                temp
            }
        }
    }

    override fun getWeather(lat: Double, long: Double, lang: String): Flow<WeatherResponse> = flow {
        emit(weatherReamoteDataSource.getWeather(lat, long, lang))

    }


    // DB
//    override fun getStoredWeather(): Flow<WeatherResponse> {
//        return weatherLocalDataSource.getWeather()
//    }
//
//    override suspend fun insertWeather(weather: WeatherResponse) {
//        weatherLocalDataSource.insertWeather(weather)
//    }
//
//    override suspend fun deleteWeather(weather: WeatherResponse) {
//        weatherLocalDataSource.delteWeather(weather)
//    }
}