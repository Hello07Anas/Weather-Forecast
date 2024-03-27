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
import com.example.weatherscope.view.viewFavorites.FavoritesLocation
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
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
        googleMap.setOnMapClickListener(this)
    }

    override fun onMapClick(point: LatLng) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1)
            if (addresses!!.isNotEmpty()) {
                val cityName = addresses.get(0).locality
                cityName?.let {
                    googleMap.clear()
                    googleMap.cameraPosition
                    googleMap.addMarker(MarkerOptions().position(point))
                    showCityDialog(cityName, point)
                }
            } else {
                Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error fetching city: ${e.message}", e)
            Toast.makeText(requireContext(), "Error fetching city", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showCityDialog(cityName: String, point: LatLng) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(cityName)
            .setMessage("Want add  \"$cityName\" To your fav?")
            .setPositiveButton("Confirm") { dialog, which ->
                addToFavorites(cityName, point)
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setCancelable(true)
            .show()
    }

    private fun addToFavorites(cityName: String, point: LatLng) {
        // Create a FavoriteLocation object to represent the favorite location
        val favoritesLocation = FavoritesLocation(cityName, point.latitude, point.longitude)

        // Get the file directory where you want to save the favorites file
        val favoritesFile = File(requireContext().filesDir, "favorites.json")
        // Read existing favorites or create a new JSONArray if the file doesn't exist
        val favoritesArray = if (favoritesFile.exists()) {
            JSONArray(favoritesFile.readText())
        } else {
            JSONArray()
        }
        // Convert the FavoriteLocation object to a JSONObject
        val favoriteLocationJSON = JSONObject().apply {
            put("cityName", favoritesLocation.cityName)
            put("latitude", favoritesLocation.latitude)
            put("longitude", favoritesLocation.longitude)
        }
        // Add the favorite location JSON object to the favorites array
        favoritesArray.put(favoriteLocationJSON)
        // Write the updated favorites array back to the file
        FileWriter(favoritesFile).use { writer ->
            writer.write(favoritesArray.toString())
            Log.d(TAG, "Favorites array: $favoritesArray")
        }
        // Show a toast message indicating the location has been added to favorites
        Toast.makeText(requireContext(), "Added $cityName to favorites", Toast.LENGTH_SHORT).show()
    }

}