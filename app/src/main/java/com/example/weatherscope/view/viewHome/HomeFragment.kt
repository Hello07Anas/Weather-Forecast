package com.example.weatherscope.view.viewHome

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherscope.R
import com.example.weatherscope.databinding.ActivityMainBinding
import com.example.weatherscope.databinding.FragmentHomeBinding
import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.network.WeatherRemoteDataSourceIMP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    lateinit var daysAdapter: DaysAdapter
    lateinit var hoursAdapter: HoursAdapter
    //lateinit var daysRecyclerView: RecyclerView
    //lateinit var hoursRecyclerView: RecyclerView
    private val weatherDataSource = WeatherRemoteDataSourceIMP()
    private val handler = Handler()

// 1 define binding variable
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun updateWeatherData(dailyWeatherData: List<WeatherResponse.DailyForecast>, hourlyWeatherData: List<WeatherResponse.HourlyForecast>) {
        daysAdapter.weatherRes = dailyWeatherData
        daysAdapter.notifyDataSetChanged()

        hoursAdapter.weatherRes = hourlyWeatherData
        hoursAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.daysOfWeek.layoutManager = LinearLayoutManager(context)
        binding.hoursOfDay.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        daysAdapter = DaysAdapter(requireContext(), emptyList())
        hoursAdapter = HoursAdapter(requireContext(), emptyList())

        binding.hoursOfDay.adapter = hoursAdapter
        binding.daysOfWeek.adapter = daysAdapter
        //daysRecyclerView.adapter = daysAdapter
//        hoursRecyclerView.adapter = hoursAdapter


        updateTime()

        // TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Testing if retrofit work or not Delete this Block
        val latitude = 30.013056
        val longitude = 31.208853

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val weatherResponse = withContext(Dispatchers.IO) {
                    weatherDataSource.getWeather(latitude, longitude)
                }
                Log.i("Success", "Weather data: $weatherResponse")
                Log.i("Sucessss ya anos", "<<<<<<<Happy>>>>>>")
                updateWeatherData(weatherResponse.daily, weatherResponse.hourly)

                //
                val ansOfTemp = convertFromKtoC(weatherResponse.daily[0].temp.day)

                binding.txtTimeZone.text = weatherResponse.timezone
                binding.txtStateOfWeather.text = weatherResponse.hourly[0].weather[0].description
                binding.txtCurrentTemp.text = ansOfTemp
                binding.txtPressure.text = weatherResponse.hourly[0].pressure.toString()
                binding.txtHumidity.text = weatherResponse.hourly[0].humidity.toString()
                binding.txtWind.text = weatherResponse.hourly[0].wind_speed.toString()
                binding.txtCloud.text = weatherResponse.hourly[0].clouds.toString()
                binding.txtUltraViolet.text = weatherResponse.hourly[0].uvi.toString()
                binding.txtVisibility.text = weatherResponse.hourly[0].visibility.toString()

            } catch (e: Exception) {
                Log.i("TAG", "Error fetching weather data: ${e.message}")
                throw e
            }
        }
        // TODO >>>>>><>>>>>>>>>>>>>>>>>>>>>>>>>>  Testing if retrofit work or not Delete this Block


    }

    private fun updateTime() {
        handler.postDelayed({
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            // Format the time
            val timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
            binding.txtDate.text = timeString

            // Call updateTime() again after 1 second to update the time
            updateTime()
        }, 1000) // 1000 milliseconds = 1 second
    }
    // next fun redundant alot :( look for better way TODO >> this fun redundant in H/D Adapter
    fun convertFromKtoC(Kelvin: Double): String {
        var ans = 0.0
        ans = Kelvin - 273.15
        ans = (ans * 10.0).toInt() / 10.0
        return "${ans.toInt()}°C"
    }
}