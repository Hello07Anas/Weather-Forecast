package com.example.weatherscope.view.viewMap

import android.app.AlertDialog
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.weatherscope.R
import com.example.weatherscope.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale
private const val TAG = "MapFragment"

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var googleMap: GoogleMap
    lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapView = view.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.setOnMapClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val cityName = addresses.get(0).locality
                cityName?.let {
                    googleMap.clear()
                    showCityDialog(cityName)
                }
            } else {
                Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error fetching city: ${e.message}", e)
            Toast.makeText(requireContext(), "Error fetching city", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCityDialog(cityName: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(cityName)
            .setMessage("What would you like to do?")
            .setPositiveButton("Favorites") { dialog, which ->
                // Handle Favorites button click
                addToFavorites(cityName)
            }
            .setNegativeButton("Alert") { dialog, which ->
                // Handle Alert button click
                setAlertForCity(cityName)
            }
            .setCancelable(true)
            .show()
    }

    private fun addToFavorites(cityName: String) {
        // Implement your logic to add the city to favorites
        Toast.makeText(requireContext(), "Added $cityName to favorites", Toast.LENGTH_SHORT).show()
    }

    private fun setAlertForCity(cityName: String) {
        // Implement your logic to set alert for the city
        Toast.makeText(requireContext(), "Set alert for $cityName", Toast.LENGTH_SHORT).show()
    }
}