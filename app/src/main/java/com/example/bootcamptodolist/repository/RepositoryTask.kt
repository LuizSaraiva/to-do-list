package com.example.bootcamptodolist.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.bootcamptodolist.dao.DaoTask
import com.example.bootcamptodolist.model.Task
import kotlinx.coroutines.flow.Flow


class RepositoryTask(val daoTask: DaoTask) {

    val allTask : Flow<List<Task>> = daoTask.getAll()

    @WorkerThread
    fun insert(task: Task){
        daoTask.insertTask(task)
    }

    @WorkerThread
    fun findId(taskId: Int): LiveData<Task> {
        return daoTask.findById(taskId)
    }

    @WorkerThread
    fun delete(task: Task){
        daoTask.deleteTask(task)
    }
}