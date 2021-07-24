package com.example.bootcamptodolist.application

import android.app.Application
import com.example.bootcamptodolist.database.DatabaseTask
import com.example.bootcamptodolist.repository.RepositoryTask

class ApplicationTask:Application() {

    val database: DatabaseTask by lazy { DatabaseTask.getDatabase(this) }
    val repository : RepositoryTask by lazy { RepositoryTask(database.daoTask()) }

}