package com.example.weatherscope.view.viewFavorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherscope.R
import com.example.weatherscope.databinding.FragmentFavoritesBinding
import org.json.JSONArray
import java.io.File
import java.io.FileWriter

class FavoritesFragment : Fragment(),  OnDeleteClickListener{

    private lateinit var binding: FragmentFavoritesBinding
    lateinit var favAdapter: FavAdapter
    // TODO here will save lon and lat and name of the place

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgAdd.setOnClickListener { // to open the map
            val navController = NavHostFragment.findNavController(this@FavoritesFragment)
            navController.navigate(R.id.action_favoritesFragment_to_FragmentMap)
        }

        val favoriteLocations = retrieveFavoriteLocationsFromFile()

        binding.listOfFav.layoutManager = LinearLayoutManager(context)
        favAdapter = FavAdapter(requireContext(), favoriteLocations.toMutableList(), this)
        binding.listOfFav.adapter =  favAdapter

        updateFavList(favoriteLocations)
    }


    fun updateFavList(favList: List<FavoritesLocation>) {
        favAdapter.favoritesLocation = favList.toMutableList()
    }

    private fun retrieveFavoriteLocationsFromFile(): List<FavoritesLocation> {
        val favoritesList = mutableListOf<FavoritesLocation>()

        try {
            val favoritesFile = File(requireContext().filesDir, "favorites.json")

            if (favoritesFile.exists()) {
                val favoritesJsonString = favoritesFile.readText()
                val favoritesArray = JSONArray(favoritesJsonString) // parsing json to jsonArr

                for (i in 0 until favoritesArray.length()) {
                    val favoriteObject = favoritesArray.getJSONObject(i)
                    val cityName = favoriteObject.getString("cityName")
                    val latitude = favoriteObject.getDouble("latitude")
                    val longitude = favoriteObject.getDouble("longitude")

                    favoritesList.add(FavoritesLocation(cityName, latitude, longitude))
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "Error retrieving favorite locations from file: ${e.message}", e)
        }

        return favoritesList
    }

    override fun onDeleteClick(position: Int) {
        val removedLocation = favAdapter.favoritesLocation.removeAt(position)
        favAdapter.notifyItemRemoved(position)

        updateFileWithRemovedLocation(removedLocation)
    }

    private fun updateFileWithRemovedLocation(removedLocation: FavoritesLocation) {
        try {
            val favoritesFile = File(requireContext().filesDir, "favorites.json")

            if (favoritesFile.exists()) {
                val favoritesJsonString = favoritesFile.readText()
                val favoritesArray = JSONArray(favoritesJsonString)

                for (i in 0 until favoritesArray.length()) {
                    val favoriteObject = favoritesArray.getJSONObject(i)
                    val cityName = favoriteObject.getString("cityName")
                    val latitude = favoriteObject.getDouble("latitude")
                    val longitude = favoriteObject.getDouble("longitude")

                    if (cityName == removedLocation.cityName &&
                        latitude == removedLocation.latitude &&
                        longitude == removedLocation.longitude
                    ) {
                        favoritesArray.remove(i)
                        break
                    }
                }
                FileWriter(favoritesFile).use { writer ->
                    writer.write(favoritesArray.toString())
                }
            }
        } catch (e: Exception) {
            Log.e("TAG", "Error updating file with removed location: ${e.message}", e)
        }
    }
}
