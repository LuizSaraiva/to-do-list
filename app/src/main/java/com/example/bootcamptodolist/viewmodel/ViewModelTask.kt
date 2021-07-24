package com.example.bootcamptodolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.bootcamptodolist.model.Task
import com.example.bootcamptodolist.repository.RepositoryTask

class ViewModelTask(val repository : RepositoryTask): ViewModel() {

    val listTask: LiveData<List<Task>> = repository.allTask.asLiveData()

    fun insert(task: Task){
        repository.insert(task)
    }

    fun findId(taskId : Int) : LiveData<Task>{
        return repository.findId(taskId)
    }

    fun delete(task: Task){
        repository.delete(task)
    }

    fun update(task: Task){
        repository.delete(task)
        repository.insert(task)
    }
}