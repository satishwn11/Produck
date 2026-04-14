package com.devsatish.produck.data.model.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "completed_tasks")
data class CompletedTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val durationMinutes: Int,

    // raw timestamp (future filters / sorting)
    val completedAtMillis: Long,

    // user readable date & time
    val completedDate: String,
    val completedTime: String
)