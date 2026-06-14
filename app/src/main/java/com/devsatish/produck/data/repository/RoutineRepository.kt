package com.devsatish.produck.data.repository

import com.devsatish.produck.data.model.datastore.GoalDataStore
import com.devsatish.produck.data.model.routine.RoutineDao
import com.devsatish.produck.data.model.routine.RoutineEntity
import kotlinx.coroutines.flow.Flow

class RoutineRepository(
    private val dao: RoutineDao,
    private val goalDataStore: GoalDataStore
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

    // data store goal
    suspend fun saveGoal(goal: String) {
        goalDataStore.saveGoal(goal)
    }

    fun getGoal(): Flow<String> {
        return goalDataStore.getGoal()
    }

}