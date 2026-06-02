package com.devsatish.produck.data.repository

import com.devsatish.produck.data.model.routine.RoutineDao
import com.devsatish.produck.data.model.routine.RoutineEntity
import kotlinx.coroutines.flow.Flow

class RoutineRepository(
    private val dao: RoutineDao
) {

    val allRoutine: Flow<List<RoutineEntity>> = dao.getAllRoutine()

    suspend fun insertRoutine(routine: RoutineEntity) {
        dao.insertRoutine(routine)
    }

    suspend fun deleteRoutine(routine: RoutineEntity) {
        dao.deleteRoutine(routine)
    }

    suspend fun updateRoutine(routine: RoutineEntity) {
        dao.updateRoutine(routine)
    }

}