package com.example.weatherscope.view.viewAlert

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherscope.R
import com.example.weatherscope.databinding.FragmentAlertsFragmentsBinding
import com.example.weatherscope.databinding.FragmentFavoritesBinding

class AlertsFragments : Fragment() {
    // TODO will do it with worker i think or can be with alarm manager
    private lateinit var binding: FragmentAlertsFragmentsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlertsFragmentsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Code will be here <><><><><><><><>
    }

}