//package com.example.weatherscope.old_dp_maybe_will_delete_it
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import kotlin.jvm.Synchronized
//import androidx.room.Room
//import androidx.room.TypeConverters
//import com.example.weatherscope.model.pojos.WeatherResponse
//
//@TypeConverters(value = [Converter::class, HourlyForecastConverter::class])
//@Database(entities = [WeatherResponse::class], version = 1)
//// TODO >> @TypeConverter and pass the clase
//abstract class AppDB: RoomDatabase() {
//    abstract fun weatherDao(): WeatherDAO
//
//    companion object {
//        private var INSTANCE: AppDB? = null
//
//        @Synchronized
//        fun getInstance(context: Context): AppDB {
//            return INSTANCE ?: synchronized(this) {
//                val db = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDB::class.java,
//                    "weather_response"
//                ).fallbackToDestructiveMigration().build()
//                INSTANCE = db
//                db
//            }
//        }
//    }
//}