package com.example.weatherscope.view.viewFavorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherscope.R
import com.example.weatherscope.databinding.FragmentFavoritesBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    // TODO the data will come from DP after save location on DP will fetch name of it and when click
    // On item from reciclyer will bring its data from DP " I think So "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgAdd.setOnClickListener {
            val navController = NavHostFragment.findNavController(this@FavoritesFragment)
            navController.navigate(R.id.action_favoritesFragment_to_FragmentMap)
        }
    }

}
