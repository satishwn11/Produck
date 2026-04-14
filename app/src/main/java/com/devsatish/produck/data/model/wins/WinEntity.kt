package com.devsatish.produck.data.model.wins

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wins")
data class WinEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val title: String,
    val description: String,
    val createdAt: Long
)