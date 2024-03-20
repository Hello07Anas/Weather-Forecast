package com.example.weatherscope.view.viewHome

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherscope.R
import com.example.weatherscope.model.pojos.WeatherResponse
import kotlin.time.Duration.Companion.hours

private const val TAG = "HoursAdapter"
class HoursAdapter(
    private val context: Context,
    var weatherRes: List<WeatherResponse.HourlyForecast>
    ):RecyclerView.Adapter<HoursAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursAdapter.ViewHolder {
        // Inflate your item layout and create a ViewHolder
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.list_of_hours, parent, false)
        Log.i(TAG, "inflated view: $v")
        val viewHolder: ViewHolder = ViewHolder(v)
        Log.i(TAG, "=========== onCreateViewHolder ===========")
        return viewHolder
    }

    override fun onBindViewHolder(holder: HoursAdapter.ViewHolder, position: Int) {
        // Bind data to your ViewHolder
        val cuurentHour = weatherRes[position]
        val ansOfTemp = convertFromKtoC(cuurentHour.temp)
        val ansOfHour = getHourNum(position)

        holder.txtTempe.text = ansOfTemp
        holder.txtTime.text = ansOfHour
        Log.i(TAG, "=========== onBindViewHolder ===========")
    }

    override fun getItemCount(): Int {
        Log.i(TAG, "getItemCount: " + weatherRes.size)
        return weatherRes.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var txtTime: TextView = itemView.findViewById(R.id.txtTime)
        var txtTempe: TextView = itemView.findViewById(R.id.txtTempe)
        // TODO next line depend on wether i think
        //var imgState: ImageView = itemView.findViewById(R.id.imgState)
    }

    // TODO following 2 fun Redandnt code in days adapter see better soulution
    fun convertFromKtoC(Kelvin: Double): String {
        var ans = 0.0
        ans = Kelvin - 273.15
        ans = (ans * 10.0).toInt() / 10.0
        return "${ans.toInt()}°C"
    }

    fun getHourNum(days: Int): String {
        return when (days) {
            1 -> "1 AM"
            2 -> "2 AM"
            3 -> "3 AM"
            4 -> "4 AM"
            5 -> "5 AM"
            6 -> "6 AM"
            7 -> "7 AM"
            8 -> "8 AM"
            9 -> "9 AM"
            10 -> "10 AM"
            11 -> "11 AM"
            12 -> "12 PM"
            13 -> "1 PM"
            14 -> "2 PM"
            15 -> "3 PM"
            16 -> "4 PM"
            17 -> "5 PM"
            18 -> "6 PM"
            19 -> "7 PM"
            20 -> "8 PM"
            21 -> "9 PM"
            22 -> "10 PM"
            23 -> "11 PM"
            24 -> "12 AM"
            25 -> "1 AM"
            26 -> "2 AM"
            27 -> "3 AM"
            28 -> "4 AM"
            29 -> "5 AM"
            30 -> "6 AM"
            31 -> "7 AM"
            32 -> "8 AM"
            33 -> "9 AM"
            34 -> "10 AM"
            35 -> "11 AM"
            36 -> "12 PM"
            37 -> "1 PM"
            38 -> "2 PM"
            39 -> "3 PM"
            40 -> "4 PM"
            41 -> "5 PM"
            42 -> "6 PM"
            43 -> "7 PM"
            44 -> "8 PM"
            45 -> "9 PM"
            46 -> "10 PM"
            47 -> "11 PM"
            48 -> "12 AM"
            else -> "Now"
        }
    }
}