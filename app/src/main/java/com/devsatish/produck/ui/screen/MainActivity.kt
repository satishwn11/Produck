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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.devsatish.produck.data.model.AppDatabase
import com.devsatish.produck.data.repository.RoutineRepository
import com.devsatish.produck.data.repository.SoundController
import com.devsatish.produck.data.repository.TimerRepository
import com.devsatish.produck.ui.viewmodel.RoutineViewModel
import com.devsatish.produck.ui.viewmodel.RoutineViewModelFactory
import com.devsatish.produck.ui.viewmodel.TimerViewModel
import com.devsatish.produck.ui.viewmodel.TimerViewModelFactory
import com.devsatish.produck.utils.notification.AlarmScheduler
import com.devsatish.produck.utils.notification.NotificationHelper
import com.devsatish.produck.utils.service.TimerForegroundService
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->

            if (isGranted) {
                AlarmScheduler.scheduleNextAlarm(this)
            }
        }

    private lateinit var repository: TimerRepository
    private lateinit var timerViewModel: TimerViewModel
    private lateinit var routineViewModel: RoutineViewModel
    private var isLoading = mutableStateOf(true)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            isLoading.value
        }

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

        val database = AppDatabase.getDatabase(this)
        repository = TimerRepository(
            database.taskDao(),
            database.winDao(),
            database.issueDao()
        )
        val routineRepository = RoutineRepository(
            database.routineDao()
        )

        timerViewModel = ViewModelProvider(
            this,
            TimerViewModelFactory(
                repository,
                soundController = SoundController(this)
            )
        )[TimerViewModel::class.java]

        routineViewModel = ViewModelProvider(
            this,
            RoutineViewModelFactory(
                routineRepository
            )
        )[RoutineViewModel::class.java]


        val serviceIntent = Intent(this, TimerForegroundService::class.java)
        startService(serviceIntent)

        bindService(serviceIntent, object : ServiceConnection {

            override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
                val service = (binder as TimerForegroundService.LocalBinder).getService()
                timerViewModel.attachService(service)

                 isLoading.value = false
            }

            override fun onServiceDisconnected(name: ComponentName?) {}
        }, BIND_AUTO_CREATE)

        // notification permission
        NotificationHelper.createChannel(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                notificationPermissionLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } else {
                AlarmScheduler.scheduleNextAlarm(this)
            }

        } else {
            AlarmScheduler.scheduleNextAlarm(this)
        }

        setContent {
            val systemUiController = rememberSystemUiController()

            systemUiController.setSystemBarsColor(
                color = Color(0xFFFAF4FF),
                darkIcons = true
            )

            RootNavigation(timerViewModel, routineViewModel)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        val stopIntent = Intent(this, TimerForegroundService::class.java)
        stopService(stopIntent)
    }

}