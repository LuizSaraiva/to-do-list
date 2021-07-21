package com.example.bootcamptodolist.datasource

import com.example.bootcamptodolist.database.DatabaseTask
import com.example.bootcamptodolist.model.Task
import kotlin.concurrent.thread

class TaskDataSource(private val databaseTask: DatabaseTask) {
    fun getList() = databaseTask.taskDao().getAll()

    fun insertTask(task: Task) {
        thread(true) {
            databaseTask.taskDao().insertTask(task)
        }
    }

    /*
     * Consultas no banco de dados com retorno do tipo LiveData<T> não necessitam que seja criada
     * uma nova thread, pois elas naturalmente já são efetuadas fora da thread principal (UI).
     */
    fun findId(taskId: Int) = databaseTask.taskDao().findById(taskId)

    fun deleteTask(task: Task) {
        thread(true) {
            databaseTask.taskDao().deleteTask(task)
        }
    }
}