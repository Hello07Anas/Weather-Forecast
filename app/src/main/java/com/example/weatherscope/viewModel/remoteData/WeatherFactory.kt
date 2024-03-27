package com.example.weatherscope.viewModel.remoteData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherscope.model.repo.WeatherRepo
import java.lang.IllegalArgumentException

class WeatherFactory(private val _irepo: WeatherRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            WeatherViewModel(_irepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}