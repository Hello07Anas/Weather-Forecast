package com.example.weatherscope.viewModel.remoteData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherscope.model.repo.WeatherRepo
import java.lang.IllegalArgumentException

class WeatherRemoteDataViewModelFactory(private val _irepo: WeatherRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherRemoteDataViewModel::class.java)) {
            WeatherRemoteDataViewModel(_irepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}