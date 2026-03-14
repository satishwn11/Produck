package com.devsatish.produck.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsatish.produck.data.repository.SoundController
import com.devsatish.produck.data.repository.TimerRepository

class TimerViewModelFactory(
    private val repository: TimerRepository,
    private val soundController: SoundController
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TimerViewModel(repository, soundController) as T
    }
}

