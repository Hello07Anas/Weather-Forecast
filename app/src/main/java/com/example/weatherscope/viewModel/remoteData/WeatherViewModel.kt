package com.example.weatherscope.viewModel.remoteData

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherscope.model.repo.WeatherRepo
import com.example.weatherscope.util.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

private const val TAG = "AllWeatherFet"
class WeatherViewModel(private val _iRepo: WeatherRepo): ViewModel() {
    // TODO Create API STATE
    private val _weatherRes: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Loading)
    val weatherRes = _weatherRes.asStateFlow()

    init {
        Log.i(TAG, "instance initalizer: Creation of ViewModel")
    }


    fun getWeather(lat: Double, long: Double, lang: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _iRepo.getWeather(lat, long, lang)
                .catch {
                    _weatherRes.value = ApiState.Error(it)
                }.collect{
                    _weatherRes.value = ApiState.Success(it)
                }
        }
    }
}