package com.devsatish.produck.data.model.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "goal_prefs")

class GoalDataStore (
    private val context: Context
) {

    companion object {
        private val GOAL_KEY = stringPreferencesKey("goal")
    }

    suspend fun saveGoal(goal: String) {
        context.dataStore.edit {
            it[GOAL_KEY] = goal
        }
    }

    fun getGoal(): Flow<String> {
        return context.dataStore.data.map {
            it[GOAL_KEY] ?: "Target?"
        }
    }

}