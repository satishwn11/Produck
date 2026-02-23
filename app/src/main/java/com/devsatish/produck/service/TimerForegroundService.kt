package com.devsatish.produck.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.devsatish.produck.MainActivity
import com.devsatish.produck.R

class TimerForegroundService : Service() {

    inner class LocalBinder : Binder() {
        fun getService(): TimerForegroundService = this@TimerForegroundService
    }

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? = binder

    fun show(title: String, running: Boolean) {
        val status = if (running) "Running" else "Paused"
        val color = if (running) 0xFF00C853.toInt() else 0xFFFFAB00.toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(this, "timer_channel")
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText(status)
            .setColor(color)
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
    }

    fun hide() {
        stopForeground(true)
        stopSelf()
    }
}