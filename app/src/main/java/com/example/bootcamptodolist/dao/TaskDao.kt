package com.example.bootcamptodolist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bootcamptodolist.model.Task

/**
 * Para consultas no banco de dados, utilizar o tipo de retorno LiveData<T> (observável).
 * Assim, sempre que alguma alteração ocorrer, as entidades observadas serão notificadas, podendo
 * assim configurar a atualização na interface de usuário conforme necessário.
 */

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun getAll() : LiveData<List<Task>>

    @Query("SELECT * FROM TASK WHERE ID = :idTask")
    fun findById(idTask: Int) : LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(vararg task:Task)

    @Delete
    fun deleteTask(task : Task)

}