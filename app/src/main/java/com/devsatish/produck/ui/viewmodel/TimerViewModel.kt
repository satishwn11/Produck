package com.devsatish.produck.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsatish.produck.data.repository.SoundController
import com.devsatish.produck.data.repository.TimerRepository
import com.devsatish.produck.utils.service.TimerForegroundService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

class TimerViewModel(
    private val repository: TimerRepository,
    private val soundController: SoundController
) : ViewModel() {


    private var timerService: TimerForegroundService? = null

    var elapsedSeconds by mutableIntStateOf(0)
        private set

    var currentTitle by mutableStateOf("")
        private set

    var isRunning by mutableStateOf(false)
        private set

    private var timerJob: Job? = null
    private var continuousSecond = 0
    private var targetSeconds = 25 * 60
    private var breakTime = 0

    var startTime = System.currentTimeMillis()

    val popularTaskTitles = repository.getTitlesOrderedByUsage()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val completedTasks = repository.getCompletedTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )


    fun startTimer(title: String, minutes: Int) {

        targetSeconds = minutes * 60
        breakTime = targetSeconds

        timerJob?.cancel()
        isRunning = false
        reset()

        currentTitle = title
        elapsedSeconds = 0
        continuousSecond = 0
        isRunning = true

        startTime = System.currentTimeMillis()

        timerService?.show(currentTitle, true)

        timerJob = viewModelScope.launch {
            while (isRunning) {

                delay(1000)

                val currentTime = System.currentTimeMillis()
                elapsedSeconds = ((currentTime - startTime) / 1000).toInt()

                if (elapsedSeconds == targetSeconds) {
                    soundController.playBell()
                    targetSeconds += breakTime
                }
            }
        }
    }

    fun pause() {
        isRunning = false
        timerJob?.cancel()
        timerService?.show(currentTitle, false)
        soundController.stopBell()
    }

    fun resume() {

        if (isRunning) return

        isRunning = true
        timerService?.show(currentTitle, true)

        startTime = System.currentTimeMillis() - elapsedSeconds * 1000

        timerJob = viewModelScope.launch {

            while (isRunning) {

                delay(1000)

                val currentTime = System.currentTimeMillis()
                elapsedSeconds = ((currentTime - startTime) / 1000).toInt()
                Log.i("Check","elapsedSed:$elapsedSeconds, targetSec:$targetSeconds")

                if (elapsedSeconds == targetSeconds) {
                    soundController.playBell()
                    if(currentTitle.isNotBlank()) {
                        targetSeconds += breakTime
                        Log.i("Check","title is not blank")
                    } else {
                        targetSeconds += 1500
                    }
                }
            }
        }
    }

    fun stopAndSave() {
            timerJob?.cancel()
            isRunning = false
            timerService?.hide()
            continuousSecond = 0
            soundController.stopBell()

            val minutes = (elapsedSeconds / 60).coerceAtLeast(1)

            viewModelScope.launch {
                repository.saveCompletedTask(
                    title = currentTitle,
                    minutes = minutes
                )
            }

            reset()
    }

    fun attachService(service: TimerForegroundService) {
        timerService = service
    }

    fun capitalizeFirst(text: String): String {
        return text.trim().replaceFirstChar { ch ->
            if (ch.isLowerCase()) ch.titlecase() else ch.toString()
        }
    }

    private fun reset() {
        elapsedSeconds = 0
        currentTitle = ""
    }

    fun formattedTime(): String {
        val h = elapsedSeconds / 3600
        val m = (elapsedSeconds % 3600) / 60
        val s = elapsedSeconds % 60
        return "%02d:%02d:%02d".format(h, m, s)
    }
}

