package com.example.bootcamptodolist.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bootcamptodolist.database.DatabaseTask
import com.example.bootcamptodolist.databinding.ActivityAddTaskBinding
import com.example.bootcamptodolist.datasource.TaskDataSource
import com.example.bootcamptodolist.extensions.format
import com.example.bootcamptodolist.extensions.text
import com.example.bootcamptodolist.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
import kotlin.concurrent.thread

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private val taskDataSource by lazy { TaskDataSource(DatabaseTask.getDatabase(this)!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            taskDataSource.findId(taskId).observe(this) {
                binding.tilTitle.text = it.title
                binding.tilData.text = it.date
                binding.tilHora.text = it.time
                binding.btnNewTask.text = "Salvar alteracao"
            }
        }
        insertListeners()
    }

    private fun insertListeners() {

        binding.tilData.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.tilData.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.tilHora.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {

                val minute =
                    if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour

                binding.tilHora.text = "${hour}:${minute}"
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER_TAG")
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.tilTitle.text,
                date = binding.tilData.text,
                time = binding.tilHora.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            thread(true) {
                taskDataSource.insertTask(task)
            }
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener { finish() }

    }

    companion object {
        const val TASK_ID = "task_id"
    }
}