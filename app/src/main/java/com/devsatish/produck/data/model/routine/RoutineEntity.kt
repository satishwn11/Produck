package com.devsatish.produck.data.model.routine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_table")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val startTime: String,
    val endTime: String,

    val title: String
)