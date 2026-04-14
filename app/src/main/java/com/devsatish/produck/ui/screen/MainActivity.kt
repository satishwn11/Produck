package com.devsatish.produck.ui.screen

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.devsatish.produck.data.model.AppDatabase
import com.devsatish.produck.data.repository.SoundController
import com.devsatish.produck.data.repository.TimerRepository
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import com.devsatish.produck.ui.viewmodel.TimerViewModelFactory
import com.devsatish.produck.utils.service.TimerForegroundService

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "timer_channel",
                    "Focus Timer",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Shows running focus timer"
                    setShowBadge(false)
                }

                val manager = getSystemService(NotificationManager::class.java)
                manager.createNotificationChannel(channel)
            }


        installSplashScreen()

        window.statusBarColor = Color(0xFFFAF4FF).toArgb()
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        val database = AppDatabase.getDatabase(this)
        val repository = TimerRepository(
            database.taskDao(),
            database.winDao(),
            database.issueDao()
        )

        val timerViewModel = ViewModelProvider(
            this,
            TimerViewModelFactory(
                repository,
                soundController = SoundController(this)
            )
        )[TimerViewModel::class.java]


        val serviceIntent = Intent(this, TimerForegroundService::class.java)
        startService(serviceIntent)

        bindService(serviceIntent, object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val service = (binder as TimerForegroundService.LocalBinder).getService()
                timerViewModel.attachService(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {}
        }, BIND_AUTO_CREATE)


        setContent {
           RootNavigation(timerViewModel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val stopIntent = Intent(this, TimerForegroundService::class.java)
        stopService(stopIntent)
    }

}