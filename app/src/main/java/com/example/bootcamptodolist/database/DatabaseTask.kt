package com.example.bootcamptodolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bootcamptodolist.dao.DaoTask
import com.example.bootcamptodolist.model.Task


@Database(entities = arrayOf(Task::class), version = 3)

abstract class DatabaseTask : RoomDatabase() {

    abstract fun daoTask(): DaoTask

    companion object {

        private const val DB_NAME = "database-task"

        private var INSTANCE: DatabaseTask? = null

        fun getDatabase(context: Context): DatabaseTask {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseTask::class.java,
                    DB_NAME
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}