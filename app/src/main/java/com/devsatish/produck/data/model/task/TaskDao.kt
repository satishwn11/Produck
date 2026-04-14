package com.devsatish.produck.data.model.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devsatish.produck.data.model.task.CompletedTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(task: CompletedTask)

    @Query("SELECT * FROM completed_tasks ORDER BY completedAtMillis DESC")
    fun getCompletedTasks(): Flow<List<CompletedTask>>

    @Query("SELECT * FROM completed_tasks ORDER BY completedAtMillis DESC")
    suspend fun getAllTasks(): List<CompletedTask>

    @Query("""
        SELECT title 
        FROM completed_tasks
        GROUP BY title
        ORDER BY COUNT(title) DESC
    """)
    fun getTitlesOrderedByUsage(): Flow<List<String>>
}