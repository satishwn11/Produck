package com.devsatish.produck.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devsatish.produck.data.model.issue.IssueDao
import com.devsatish.produck.data.model.issue.IssueEntity
import com.devsatish.produck.data.model.routine.RoutineDao
import com.devsatish.produck.data.model.routine.RoutineEntity
import com.devsatish.produck.data.model.task.CompletedTask
import com.devsatish.produck.data.model.task.TaskDao
import com.devsatish.produck.data.model.wins.WinDao
import com.devsatish.produck.data.model.wins.WinEntity

@Database(
    entities = [
        CompletedTask::class,
        WinEntity::class,
        IssueEntity::class,
        RoutineEntity::class],
    version = 4
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun winDao(): WinDao
    abstract fun issueDao(): IssueDao
    abstract fun routineDao(): RoutineDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "produck_db"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS issues (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                category TEXT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                createdAt INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS wins (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                category TEXT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                createdAt INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("""
            CREATE TABLE IF NOT EXISTS routine_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                startTime TEXT NOT NULL,
                endTime TEXT NOT NULL,
                title TEXT NOT NULL
            )
        """.trimIndent())
    }
}