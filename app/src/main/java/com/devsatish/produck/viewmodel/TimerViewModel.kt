package com.devsatish.produck.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsatish.produck.repository.SoundController
import com.devsatish.produck.repository.TimerRepository
import com.devsatish.produck.service.TimerForegroundService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
    private var targetseconds = 25 * 60




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


    fun startTimer(
        title: String,
        minutes: Int
    ) {
        targetseconds = minutes * 60

        // kill old timer
        timerJob?.cancel()
        isRunning = false
        reset()

        currentTitle = title
        elapsedSeconds = 0
        continuousSecond = 0
        isRunning = true
        timerService?.show(currentTitle, true)

        timerJob = viewModelScope.launch {
            while (isRunning) {
                delay(1000)
                elapsedSeconds++
                continuousSecond++

                if (continuousSecond == targetseconds) {
                    soundController.playBell()
                }
            }
        }
    }

    fun pause() {
        isRunning = false
        timerJob?.cancel()
        timerService?.show(currentTitle, false)
        continuousSecond = 0
        soundController.stopBell()
    }

    fun resume() {
        if (isRunning) return
        isRunning = true
        timerService?.show(currentTitle, true)

        timerJob = viewModelScope.launch {
            while (isRunning) {
                delay(1000)
                elapsedSeconds++
                continuousSecond++

                if (continuousSecond == targetseconds) {
                    soundController.playBell()
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

