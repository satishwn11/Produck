package com.devsatish.produck.utils.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReminderReceiver : BroadcastReceiver() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onReceive(context: Context, intent: Intent?) {

        val notification = NotificationCompat.Builder(
            context,
            "daily_reminder_channel"
        )
            .setSmallIcon(android.R.drawable.ic_input_add)
            .setContentTitle("Reminder")
            .setContentText("It's time to analyze your day!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context)
            .notify(1001, notification)

        AlarmScheduler.scheduleNextAlarm(context)
    }
}