package com.devsatish.produck.data.repository

import com.devsatish.produck.data.model.task.CompletedTask
import com.devsatish.produck.data.model.task.TaskDao
import com.devsatish.produck.data.model.issue.IssueDao
import com.devsatish.produck.data.model.issue.IssueEntity
import com.devsatish.produck.data.model.wins.WinDao
import com.devsatish.produck.data.model.wins.WinEntity
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimerRepository(
    private val dao: TaskDao,
    private val winDao: WinDao,
    private val issueDao: IssueDao
) {

    val wins = winDao.getAllWins()
    val issues = issueDao.getAllIssue()

    suspend fun saveCompletedTask(title: String, minutes: Int) {

        val now = System.currentTimeMillis()
        val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(now))
        val time = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(now))

        dao.insertTask(
            CompletedTask(
                title = title,
                durationMinutes = minutes,
                completedAtMillis = now,
                completedDate = date,
                completedTime = time
            )
        )
    }

    fun getCompletedTasks(): Flow<List<CompletedTask>> {
        return dao.getCompletedTasks()
    }

    fun getTitlesOrderedByUsage(): Flow<List<String>> {
        return dao.getTitlesOrderedByUsage()
    }

    suspend fun insertWin(win: WinEntity) {
        winDao.insertWin(win)
    }

    suspend fun deleteWin(win: WinEntity) {
        winDao.deleteWin(win)
    }

    suspend fun insertIssue(issue: IssueEntity) {
        issueDao.insertIssue(issue)
    }

    suspend fun deleteIssue(issue: IssueEntity) {
        issueDao.deleteIssue(issue)
    }
}
