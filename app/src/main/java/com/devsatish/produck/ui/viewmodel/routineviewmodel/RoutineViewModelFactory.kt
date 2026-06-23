package com.devsatish.produck.ui.viewmodel.routineviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devsatish.produck.data.repository.RoutineRepository

class RoutineViewModelFactory(
    private val repository: RoutineRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RoutineViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return RoutineViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel")
    }
}