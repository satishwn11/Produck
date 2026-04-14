package com.devsatish.produck.data.model.wins

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WinDao {

        @Insert
        suspend fun insertWin(win: WinEntity)

        @Delete
        suspend fun deleteWin(win: WinEntity)

        // Live UI ke liye (recommended)
        @Query("SELECT * FROM wins ORDER BY createdAt DESC")
        fun getAllWins(): Flow<List<WinEntity>>

        // One time fetch (background work / debug)
        @Query("SELECT * FROM wins ORDER BY createdAt DESC")
        suspend fun getAllWinsList(): List<WinEntity>

        // Category ke hisaab se filter
        @Query("SELECT * FROM wins WHERE category = :category ORDER BY createdAt DESC")
        fun getWinsByCategory(category: String): Flow<List<WinEntity>>

        // Most used category (same logic jo tumne title me use kiya)
        @Query("""
        SELECT category 
        FROM wins
        GROUP BY category
        ORDER BY COUNT(category) DESC
    """)
        fun getCategoriesByUsage(): Flow<List<String>>
}