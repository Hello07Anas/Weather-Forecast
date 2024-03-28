package com.example.weatherscope.viewModel.remoteData

import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.model.repo.WeatherRepo
import com.example.weatherscope.util.ApiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


@ExperimentalCoroutinesApi
class WeatherViewModelTest {
//    @Test
//    fun `test getWeather success`() = runBlockingTest {
//        // Mock dependencies
//        val weatherRepo = mockk<WeatherRepo>()
//        val viewModel = WeatherViewModel(weatherRepo)
//
//        // Stubbing repository method
//        val mockWeatherResponse = WeatherResponse(
//            id = 1L,
//            daily = listOf(
//                WeatherResponse.DailyForecast(/* provide parameters for DailyForecast */)
//            ),
//            hourly = listOf(
//                WeatherResponse.HourlyForecast(/* provide parameters for HourlyForecast */)
//            ),
//            lat = 0.0, // example latitude
//            lon = 0.0, // example longitude
//            timezone = "UTC", // example timezone
//            timezone_offset = 0 // example timezone offset
//        )
//        coEvery { weatherRepo.getWeather(any(), any(), any()) } returns flow {
//            emit(mockWeatherResponse)
//        }
//
//        // Call the method under test
//        viewModel.getWeather(0.0, 0.0, "en")
//
//        // Verify state flow emits loading and then success
//        viewModel.weatherRes.collect { apiState ->
//            assertThat(apiState, equalTo(ApiState.Loading))
//            assertThat(apiState, equalTo(ApiState.Success(mockWeatherResponse)))
//        }
//    }
//
//    @Test
//    fun `test getWeather error`() = runBlockingTest {
//        // Mock dependencies
//        val weatherRepo = mockk<WeatherRepo>()
//        val viewModel = WeatherViewModel(weatherRepo)
//
//        // Stubbing repository method to throw an error
//        val mockError = Exception("Mock error")
//        coEvery { weatherRepo.getWeather(any(), any(), any()) } throws mockError
//
//        // Call the method under test
//        viewModel.getWeather(0.0, 0.0, "en")
//
//        // Verify state flow emits loading and then error
//        viewModel.weatherRes.collect { apiState ->
//            assertThat(apiState, equalTo(ApiState.Loading))
//            assertThat(apiState, equalTo(ApiState.Error(mockError)))
//        }
//    }
}