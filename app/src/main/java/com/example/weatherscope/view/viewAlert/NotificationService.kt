package com.example.weatherscope.view.viewAlert

import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class NotificationService : NotificationListenerService() {

    private val NOTIFICATION_ID = 123

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)

        if (sbn?.id == NOTIFICATION_ID) {
            // Send a broadcast to your AlarmReceiver to stop the alarm
            val intent = Intent("STOP_ALARM")
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (sbn?.id == NOTIFICATION_ID) {
            // You can perform additional actions here when the notification is posted
            // For example, you can update the UI or perform other tasks
        }
    }
}
