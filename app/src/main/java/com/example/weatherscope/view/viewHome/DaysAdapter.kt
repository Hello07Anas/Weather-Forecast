package com.example.weatherscope.view.viewHome

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherscope.R
import com.example.weatherscope.model.pojos.WeatherResponse
import com.example.weatherscope.util.MySharedPreferences


private const val TAG = "DaysAdapter"
class DaysAdapter(
    private val context: Context,
    var weatherRes: List<WeatherResponse.DailyForecast>
): RecyclerView.Adapter<DaysAdapter.ViewHolder>() {

    lateinit var mySharedPreferences: MySharedPreferences

    // Implement onCreateViewHolder method
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate your item layout and create a ViewHolder
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.list_of_days, parent, false)
        Log.d(TAG, "Inflated view: $v")
        val viewHolder: ViewHolder = ViewHolder(v)
        Log.i(TAG, "=========== onCreateViewHolder ===========")
        mySharedPreferences = MySharedPreferences(context)
        return viewHolder
    }

    // Implement onBindViewHolder method
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to your ViewHolder
        val cuurentDay = weatherRes[position]
        val ansOfDayName = getDayName(position)
        //val ansOfTemp = convertFromKtoC(cuurentDay.temp.day)
        val ansOfTemp = cuurentDay.temp.day
        val descriptions = cuurentDay.weather.map { it.description }.joinToString()

        holder.txtDay.text = ansOfDayName
        holder.txtTemp.text = ansOfTemp.toString()
        holder.txtStateWeather.text = descriptions
        // TODO image of "stateImg"
        // Handel it with if statement depend on weatherState put image from drawable.
        Log.i(TAG, "=========== onBindViewHolder ===========")
    }

    // Implement getItemCount method
    override fun getItemCount(): Int {
        // Return the size of your data
        Log.i(TAG, "getItemCount: " + weatherRes.size)
        return weatherRes.size
    }

    // Define your ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define your ViewHolder components here
        var txtDay: TextView = itemView.findViewById(R.id.txtDay)
        var txtStateWeather: TextView = itemView.findViewById(R.id.txtStateWeather)
        var txtTemp: TextView = itemView.findViewById(R.id.txtTemp)
        //var stateImg: TextView = itemView.findViewById(R.id.stateImg)
    }

    fun getDayName(days: Int): String {
        if(mySharedPreferences.isLangEn) {
            return when (days) {
                1 -> "Sunday"
                2 -> "Monday"
                3 -> "Tuesday"
                4 -> "Wednesday"
                5 -> "Thursday"
                6 -> "Friday"
                7 -> "Saturday"
                else -> "Tomorrow"
            }
        } else {
            return when (days) {
                1 -> "الأحد"
                2 -> "الأثنين"
                3 -> "الثلاثاء"
                4 -> "الاربعـاء"
                5 -> "الخميس"
                6 -> "الجمعة"
                7 -> "السبت"
                else -> "غدا"
            }
        }
    }

    fun convertFromKtoC(Kelvin: Double): String {
        var ans = 0.0
        ans = Kelvin - 273.15
        ans = (ans * 10.0).toInt() / 10.0
        return "${ans.toInt()}°C"
    }
}