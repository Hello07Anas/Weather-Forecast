package com.example.weatherscope.model.repo

import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.network.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherRepoIMP(
    // TODO here will make ref form remote Data and local
    private var weatherReamoteDataSource: WeatherRemoteDataSource
) : WeatherRepo {


    companion object { // insted of static in java
        private var instance: WeatherRepoIMP? = null
        fun getInstance(
            weatherReamoteDataSource: WeatherRemoteDataSource,
            // TODO here localData
        ): WeatherRepoIMP {
            return instance?: synchronized(this) {
                val temp = WeatherRepoIMP(
                    weatherReamoteDataSource)//, TODO adding local Data too
                instance = temp
                temp
            }
        }
    }

    // TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete this Block
    val latitude = 30.013056
    val longitude = 31.208853
    // TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete this Block

    override fun getWeather(): Flow<WeatherResponse> = flow {
        emit(weatherReamoteDataSource.getWeather(latitude, longitude))
    }
}