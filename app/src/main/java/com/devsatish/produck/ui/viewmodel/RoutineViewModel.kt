package com.devsatish.produck.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsatish.produck.data.model.routine.RoutineEntity
import com.devsatish.produck.data.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoutineViewModel(
    private val repository: RoutineRepository
) : ViewModel() {

    val allRoutine: Flow<List<RoutineEntity>> = repository.allRoutine

    fun addRoutine(
        startTime: String,
        endTime: String,
        title: String
    ) {

        viewModelScope.launch {

            repository.insertRoutine(
                RoutineEntity(
                    startTime = startTime,
                    endTime = endTime,
                    title = title
                )
            )
        }
    }

    fun deleteItem(routine: RoutineEntity) {
        viewModelScope.launch {
            repository.deleteRoutine(routine)
        }
    }

    fun updateItem(routine: RoutineEntity) {
        viewModelScope.launch {
            repository.updateRoutine(routine)
        }
    }

}