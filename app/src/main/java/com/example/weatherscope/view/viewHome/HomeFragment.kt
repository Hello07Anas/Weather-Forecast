package com.example.weatherscope.view.viewHome

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherscope.databinding.FragmentHomeBinding
import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.model.repo.WeatherRepoIMP
import com.example.weatherscope.network.WeatherRemoteDataSource
import com.example.weatherscope.network.WeatherRemoteDataSourceIMP
import com.example.weatherscope.util.ApiState
import com.example.weatherscope.util.MySharedPreferences
import com.example.weatherscope.viewModel.remoteData.WeatherFactory
import com.example.weatherscope.viewModel.remoteData.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Calendar
import java.util.Locale

const val  REQUEST_LOCATION_CODE = 2007
private val TAG = "HOME_FRAGMENT"
class HomeFragment : Fragment() { // TODO >> take lat and lang in shared prefrence and pass it to alertFragment

    lateinit var daysAdapter: DaysAdapter
    lateinit var hoursAdapter: HoursAdapter
    private val weatherDataSource = WeatherRemoteDataSourceIMP()
    lateinit var mySharedPreferences: MySharedPreferences
    lateinit var weatherViewModel: WeatherViewModel
    private val handler = Handler() // deprecated عشان المبرمجين مش بيستخدموه صح زيي كدا غالبا :D  << TODO أمسح الكومنت
    lateinit var binding: FragmentHomeBinding
    // allow me to get last known location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude = 24.468381
    var longitude = 46.675297
    var lang = "en"
    private var isLocationReady = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        mySharedPreferences = MySharedPreferences(requireContext())
        setupViewModel()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                weatherViewModel.weatherRes.collectLatest { status ->
                    when (status) {
                        is ApiState.Loading -> {
                            Log.d(TAG, "observeWeather: Loading")
                        }

                        is ApiState.Success -> {
                            Log.d(TAG, "onCreateView: Success")
                            bind(status.data)
                        }

                        is ApiState.Error -> {
                            Log.e(TAG, "onCreateView: Error ${status.message}")
                        }
                    }
                }
            }
        }

        if(mySharedPreferences.isLangEn) {lang = "en"} else {lang = "ar"}

        return binding.root
    }

    private fun setupViewModel() {
        val repository = WeatherRepoIMP.getInstance(WeatherRemoteDataSourceIMP())
        val viewModelFactory = WeatherFactory(repository)
        weatherViewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                Log.i(TAG, "Location permissions and services are enabled")
                getFreshLocation()
            } else {
                enableLocationServices()
            }
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_CODE
            )
        }
    }

    private fun isLocationEnabled(): Boolean { // this function checks if gps or network is enabled or not
        val locationManager: LocationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        Log.i(TAG, "Checking if location is enabled")

        return true
    }

    @SuppressLint("MissingPermission")
    private fun getFreshLocation() {
        Log.i(TAG, "In getFreshLocation()")
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest.Builder(0).apply {
                setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            }.build(),
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    fusedLocationProviderClient.removeLocationUpdates(this)
                    val location = locationResult.lastLocation
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                        Log.i(TAG, "Latitude: $latitude, Longitude: $longitude")
                        getAddress(latitude, longitude)
                        // location is ready
                        isLocationReady = true
                        // Make the API call only when location is ready
                        weatherViewModel.getWeather(latitude, longitude, lang)
                    } else {
                        Log.e(TAG, "Location result is null")
                    }
                }
            },
            Looper.myLooper()
        )
    }

    private fun getAddress(latitude: Double, longitude: Double) {
        if (!isAdded) {
            return
        }
        val geocoder = Geocoder(requireContext())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0].getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun enableLocationServices() {
        Toast.makeText(requireContext(), "Pleas turn on location", Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun checkPermissions(): Boolean {
        val fineLocationPermissionState = ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermissionState = ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
        return fineLocationPermissionState == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermissionState == PackageManager.PERMISSION_GRANTED
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.daysOfWeek.layoutManager = LinearLayoutManager(context)
        binding.hoursOfDay.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        daysAdapter = DaysAdapter(requireContext(), emptyList())
        hoursAdapter = HoursAdapter(requireContext(), emptyList())

        binding.hoursOfDay.adapter = hoursAdapter
        binding.daysOfWeek.adapter = daysAdapter

        updateTime()


    }

    fun bind(weatherResponse: WeatherResponse) {
        Log.i(TAG, "Weather data: $weatherResponse")
        Log.i(TAG, "<<<<<<<Happy>>>>>>")
        updateWeatherData(weatherResponse.daily, weatherResponse.hourly)
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
    }

    private fun updateTime() {
        handler.postDelayed({
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            val timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second)
            binding.txtDate.text = timeString

            updateTime()
        }, 1000)
    }
    // next fun redundant alot :( look for better way TODO >> this fun redundant in H/D Adapter
    fun convertFromKtoC(Kelvin: Double): String {
        var ans = 0.0
        ans = Kelvin - 273.15
        ans = (ans * 10.0).toInt() / 10.0
        return "${ans.toInt()}°C"
    }

    fun updateWeatherData(dailyWeatherData: List<WeatherResponse.DailyForecast>, hourlyWeatherData: List<WeatherResponse.HourlyForecast>) {
        daysAdapter.weatherRes = dailyWeatherData
        daysAdapter.notifyDataSetChanged()

        hoursAdapter.weatherRes = hourlyWeatherData
        hoursAdapter.notifyDataSetChanged()
    }
}