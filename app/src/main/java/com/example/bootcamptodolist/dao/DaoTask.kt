package com.example.bootcamptodolist.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bootcamptodolist.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoTask {

    @Query("SELECT * FROM task")
    fun getAll() : Flow<List<Task>>

    @Query("SELECT * FROM TASK WHERE ID = :idTask")
    fun findById(idTask: Int) : LiveData<Task>

    @Insert
    fun insertTask(task:Task)

    @Delete
    fun deleteTask(task : Task)

}