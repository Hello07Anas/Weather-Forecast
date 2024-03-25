package com.example.weatherscope.util

import android.content.Context

class MySharedPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    var isLangEn: Boolean
        get() = sharedPreferences.getBoolean("isLangEn", true)
        set(value) = editor.putBoolean("isLangEn", value).apply()

    var isEnablednotification: Boolean
        get() = sharedPreferences.getBoolean("isEnablednotification", false)
        set(value) = editor.putBoolean("isEnablednotification", value).apply()

    var isMeterPerSecTheWindSpeed: Boolean
        get() = sharedPreferences.getBoolean("isMeterPerSecTheWindSpeed", false)
        set(value) = editor.putBoolean("isMeterPerSecTheWindSpeed", value).apply()

    var isLocationUsingMap: Boolean
        get() = sharedPreferences.getBoolean("isLocationUsingMap", false)
        set(value) = editor.putBoolean("isLocationUsingMap", value).apply()

    var temperatureType: String
        get() = sharedPreferences.getString("temperatureType", "C") ?: "C"
        set(value) = editor.putString("temperatureType", value).apply()


    // TODO if have time apply and use next fun to reset settings
//    fun resetSettings() {
//        editor.clear().apply()
//    }
}