package com.devsatish.produck.data.model.issue

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IssueDao {

    @Insert
    suspend fun insertIssue(issue: IssueEntity)

    @Delete
    suspend fun deleteIssue(issue: IssueEntity)

    // Live UI ke liye (recommended)
    @Query("SELECT * FROM issues ORDER BY createdAt DESC")
    fun getAllIssue(): Flow<List<IssueEntity>>

    // One time fetch (background work / debug)
    @Query("SELECT * FROM issues ORDER BY createdAt DESC")
    suspend fun getAllIssueList(): List<IssueEntity>

    // Category ke hisaab se filter
    @Query("SELECT * FROM issues WHERE category = :category ORDER BY createdAt DESC")
    fun getWinsByCategory(category: String): Flow<List<IssueEntity>>

    // Most used category (same logic jo tumne title me use kiya)
    @Query("""
        SELECT category 
        FROM issues
        GROUP BY category
        ORDER BY COUNT(category) DESC
    """)
    fun getCategoriesByUsage(): Flow<List<String>>
}