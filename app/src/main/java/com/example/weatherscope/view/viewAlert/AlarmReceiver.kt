package com.example.weatherscope.view.viewAlert

import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.weatherscope.R
import com.example.weatherscope.util.MySharedPreferences

class AlarmReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "AlarmChannel"
    private val NOTIFICATION_ID = 123
    private lateinit var mySharedPreferences: MySharedPreferences
    private var alarmSound: Ringtone? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        mySharedPreferences = context?.let { MySharedPreferences(it) }!!

        when (intent?.action) {
            "CANCEL_ALARM" -> cancelAlarm()
            else -> {
                showNotification(context)
                startAlarmSound(context)
            }
        }
    }

    private fun showNotification(context: Context?) {
        // Create notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarm Channel"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Set the alarm sound
        val alarmSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        // Build notification
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.nav_alert)
            .setContentTitle(mySharedPreferences.nameOfLocation) // TODO will get it name of location
            .setContentText(mySharedPreferences.desOfLocation) // TODO will get it description of location
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(alarmSoundUri) // Set the sound for the notification
            .addAction(R.drawable.nav_alert2, "stop alarm", cancelPendingIntent(context)) // Add cancel action

        // Check if the app has the necessary permission to show notifications
        if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.RECEIVE_BOOT_COMPLETED) } != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, handle it accordingly
            // You may prompt the user to grant the permission or handle it in another way
            return
        }

        // Permission is granted, proceed to show the notification
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun cancelPendingIntent(context: Context): PendingIntent {
        val cancelIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = "CANCEL_ALARM"
        }
        return PendingIntent.getBroadcast(context, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }



    private fun startAlarmSound(context: Context?) {
        if (alarmSound == null || !alarmSound!!.isPlaying) {
            val alarmSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            alarmSound = RingtoneManager.getRingtone(context, alarmSoundUri)
            alarmSound?.play()
        }
        cancelNotification(context)
    }


    private fun cancelNotification(context: Context?) {
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.cancel(NOTIFICATION_ID)
    }

    private fun cancelAlarm() {
        alarmSound?.stop()
        alarmSound = null
    }
}
