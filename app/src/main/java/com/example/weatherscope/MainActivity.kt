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
import com.example.weatherscope.network.WeatherRemoteDataSourceIMP
import com.example.weatherscope.view.viewAlert.AlertsFragments
import com.example.weatherscope.view.viewFavorites.FavoritesFragment
import com.example.weatherscope.view.viewHome.HomeFragment
import com.example.weatherscope.view.viewSetting.SettingsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Testing if retrofit work or not Delete this Block
    private val weatherDataSource = WeatherRemoteDataSourceIMP()
    // TODO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Testing if retrofit work or not Delete this Block
    private lateinit var drawLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            } catch (e: Exception) {
                Log.i("TAG", "Error fetching weather data: ${e.message}")
                throw e
            }
        }
        // TODO >>>>>><>>>>>>>>>>>>>>>>>>>>>>>>>>  Testing if retrofit work or not Delete this Block

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()

            navigationView.setCheckedItem(R.id.nav_home)
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                toolbar.title = "Home"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment())
                    .commit()
            }
            R.id.nav_alert -> {
                toolbar.title = "Alerts"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, AlertsFragments())
                    .commit()
            }
            R.id.nav_setting -> {
                toolbar.title = "Settings"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SettingsFragment())
                    .commit()
            }
            R.id.nav_fav -> {
                toolbar.title = "Favorites"
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, FavoritesFragment())
                    .commit()
            }
            R.id.nav_logout -> {
                finish()
            }
        }
        drawLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawLayout.isDrawerOpen(GravityCompat.START)) {
            drawLayout.closeDrawer(GravityCompat.START)
        } else {
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
