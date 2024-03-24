package com.example.weatherscope

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weatherscope.databinding.ActivityMainBinding
import com.example.weatherscope.network.WeatherRemoteDataSourceIMP
import com.example.weatherscope.view.viewAlert.AlertsFragments
import com.example.weatherscope.view.viewFavorites.FavoritesFragment
import com.example.weatherscope.view.viewHome.HomeFragment
import com.example.weatherscope.view.viewSetting.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigationView = binding.bottomNavigationBar
        navController = Navigation.findNavController(this, R.id.mainFragment)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            val itemId = item.itemId
            if (itemId == R.id.FragmentHome) {
                navigateToFragment(R.id.FragmentHome)
            } else if (itemId == R.id.favoritesFragment) {
                navigateToFragment(R.id.favoritesFragment)
            } else if (itemId == R.id.settingsFragment) {
                navigateToFragment(R.id.settingsFragment)
            } else if (itemId == R.id.alertsFragments) {
                navigateToFragment(R.id.alertsFragments)
            }
            true
        }

    }

    private fun navigateToFragment(fragmentId: Int) {
        if (navController.currentDestination?.id != fragmentId) {
            navController.popBackStack(R.id.FragmentHome, false)
            navController.navigate(fragmentId)
        }
    }

    override fun onBackPressed() {
        if (!navController.navigateUp()) {
            super.onBackPressed()
        }
    }
}

// TODO remove next comments
/*

    Api key: 51480a89243b1c0fde77f3cf9c774157
    Lat: 30.013056
    Long: 31.208853

------------------------------------------------------------------

    // current weather
    https://api.openweathermap.org/data/2.5/weather?lat=30.013056&lon=31.208853&appid=51480a89243b1c0fde77f3cf9c774157

    // daily weather
    https://api.openweathermap.org/data/2.5/forecast/daily?lat=30.013056&lon=31.208853&cnt={cnt}&appid=51480a89243b1c0fde77f3cf9c774157

    // hourly weather
    https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=30.013056&lon=31.208853&appid=51480a89243b1c0fde77f3cf9c774157

*/
