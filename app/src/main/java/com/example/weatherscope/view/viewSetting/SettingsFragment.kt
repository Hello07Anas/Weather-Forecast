package com.example.weatherscope.view.viewSetting

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weatherscope.databinding.FragmentSettingsBinding
import com.example.weatherscope.util.MySharedPreferences
import java.util.Locale

class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var myPrefs: MySharedPreferences

    // TODO next to line will be the shared prefrences i think
    private var currentLocale: Locale = Locale.getDefault()
    private var isEnablednotification: Boolean = false
    private var isMeterPerSecTheWindSpeed: Boolean = false
    private var isLocationUsingMap:Boolean = false
    private var temperatureType: String = "C" //  C -> celsius     K  ->  Kelvin     F -> Fahrenheit
    private var isLangEn: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        myPrefs = MySharedPreferences(requireContext()) // to intial myPrefs
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // next 5 methods just to set defult settings TODO rename them in good name
        updateLanguageCheckbox()
        updateNotifications()
        updateWindSpeed()
        updateLocation()
        updateTemperature()

        println("===============Test===============")
        println(myPrefs.isLangEn)
        println("^^ lang")
        println(myPrefs.isEnablednotification)
        println("^^ notification")
        println(myPrefs.temperatureType)
        println("^^ Temp")
        println(myPrefs.isLocationUsingMap)
        println("^^ location")
        println(myPrefs.isMeterPerSecTheWindSpeed)
        println("^^ wind")
        println("===============Test===============")


        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        binding.checkBoxArabic.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxEnglish.isChecked = false
                if (currentLocale != Locale("ar")) {
                    setLocale("ar")
                    isLangEn = false
                    myPrefs.isLangEn = false
                    println("===============Test===============")
                    println(myPrefs.isLangEn)
                    println("^^ lang")
                    Toast.makeText(requireContext(), "تم تفعيل اللغلة العربية", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.checkBoxEnglish.isChecked = true
            }
        }
        // ==================================== Language ===========================================
        binding.checkBoxEnglish.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxArabic.isChecked = false
                if (currentLocale != Locale("en")) {
                    setLocale("en")
                    isLangEn = true
                    myPrefs.isLangEn = true
                    Toast.makeText(requireContext(), "English lang is Activated", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.checkBoxArabic.isChecked = true
            }
        }
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        binding.checkBoxDisable.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.checkBoxEnable.isChecked = false
                disable()
            } else {
                binding.checkBoxEnable.isChecked = true
                enable()
            }
        }
        // ==================================== Notifications ======================================
        binding.checkBoxEnable.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                binding.checkBoxDisable.isChecked = false
                enable()
            } else {
                binding.checkBoxDisable.isChecked = true
                disable()
            }
        }
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        binding.checkBoxMeterVsSec.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.checkBoxMileVsHour.isChecked = false
                isMeterPerSecTheWindSpeed = true
                myPrefs.isMeterPerSecTheWindSpeed = true
            } else {
                binding.checkBoxMileVsHour.isChecked = true
                isMeterPerSecTheWindSpeed = false
                myPrefs.isMeterPerSecTheWindSpeed = false
            }
        }
        // ====================================== Wind Speed =======================================
        binding.checkBoxMileVsHour.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.checkBoxMeterVsSec.isChecked = false
                isMeterPerSecTheWindSpeed = false
                myPrefs.isMeterPerSecTheWindSpeed = false
            } else {
                binding.checkBoxMeterVsSec.isChecked = true
                isMeterPerSecTheWindSpeed = true
                myPrefs.isMeterPerSecTheWindSpeed = true
            }
        }
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        binding.checkBoxMap.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxGBS.isChecked = false
                isLocationUsingMap = true
                myPrefs.isLocationUsingMap = true
            } else {
                binding.checkBoxGBS.isChecked = true
                isLocationUsingMap = false
                myPrefs.isLocationUsingMap = false
            }
        }
        // ====================================== Location =========================================
        binding.checkBoxGBS.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.checkBoxMap.isChecked = false
                isLocationUsingMap = false
                myPrefs.isLocationUsingMap = false
            } else {
                binding.checkBoxMap.isChecked = true
                isLocationUsingMap = true
                myPrefs.isLocationUsingMap = true
            }
        }
        // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        binding.checkBoxCelsuis.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                temperatureType = "C"
                myPrefs.temperatureType = "C"
//                setDefultTemp()
                binding.checkBoxFahrenheight.isChecked = false
                binding.checkBoxKelvin.isChecked = false
                Toast.makeText(requireContext(), "temp set to Celsuis.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.checkBoxFahrenheight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                temperatureType = "F"
                myPrefs.temperatureType = "F"
//                setDefultTemp()
                binding.checkBoxCelsuis.isChecked = false
                binding.checkBoxKelvin.isChecked = false
                Toast.makeText(requireContext(), "temp set to Fahrenheight.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.checkBoxKelvin.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                temperatureType = "K"
                myPrefs.temperatureType = "K"
//                setDefultTemp()
                binding.checkBoxCelsuis.isChecked = false
                binding.checkBoxFahrenheight.isChecked = false
                Toast.makeText(requireContext(), "temp set to Kelvin.", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Heaper Meathods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    // Heaper Methods for Temperature  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private fun updateTemperature() {  //temperatureType
        binding.checkBoxCelsuis.isChecked = true
    }

//    private fun setDefultTemp() {
//        binding.checkBoxCelsuis.isChecked = true
//    }

    // Heaper Methods for Location >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private fun updateLocation() {
        if (binding.checkBoxFahrenheight.isChecked == false && binding.checkBoxKelvin.isChecked == false) {
            binding.checkBoxGBS.isChecked = true
            temperatureType = "C"
            myPrefs.temperatureType = "C"
        }
    }

    // Heaper Methods for Wind Speed  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private fun updateWindSpeed() {
        binding.checkBoxMileVsHour.isChecked = true
    }
    // Heaper Methods for Notifications  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private fun enable() {
        // TODO implement this methods
        isEnablednotification = true
        myPrefs.isEnablednotification = true
        Toast.makeText(requireContext(), "enabled", Toast.LENGTH_SHORT).show()
    }

    private fun disable() {
        // TODO implement this methods
        isEnablednotification = false
        myPrefs.isEnablednotification = false
        Toast.makeText(requireContext(), "disabled", Toast.LENGTH_SHORT).show()
    }

    private fun updateNotifications() {
        binding.checkBoxDisable.isChecked = true
    }
    // Healper Methods for lang  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    private fun updateLanguageCheckbox() {
        binding.checkBoxArabic.isChecked = currentLocale.language == "ar"
        binding.checkBoxEnglish.isChecked = currentLocale.language != "ar"
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        currentLocale = locale
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()
    }
}
