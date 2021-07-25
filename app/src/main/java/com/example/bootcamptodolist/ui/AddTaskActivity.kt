package com.example.bootcamptodolist.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bootcamptodolist.R
import com.example.bootcamptodolist.databinding.ActivityAddTaskBinding
import com.example.bootcamptodolist.application.ApplicationTask
import com.example.bootcamptodolist.viewmodel.ViewModelTask
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

    private val viewModel: ViewModelTask by lazy {
        ViewModelTask((application as ApplicationTask).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            viewModel.findId(taskId)?.observe(this) { it ->
                binding.tilTitle.text = it.title
                binding.tilData.text = it.date
                binding.tilHora.text = it.time
                binding.tilResume.text = it.resume
                binding.tilDescription.text = it.description

                binding.toolbar.title = getString(R.string.title_edit_task)

                binding.cbStatus.let { cb ->
                    cb.isChecked = it.status
                    cb.visibility = View.VISIBLE
                }

                binding.tvStatus.let { tv ->
                    tv.visibility = View.VISIBLE
                    if (it.status) {
                        binding.tvStatus.text = getString(R.string.status_finish)
                    }

                }

                binding.btnNewTask.text = getString(R.string.save)
            }
        }
        insertListeners()
    }

    private fun insertListeners() {

        binding.cbStatus.setOnClickListener {
            if (binding.cbStatus.isChecked) {
                binding.tvStatus.text = getText(R.string.status_finish)
            } else {
                binding.tvStatus.text = (getText(R.string.status_pending))
            }
        }

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

            val title = binding.tilTitle.text
            val date = binding.tilData.text
            val time = binding.tilHora.text
            val resume = binding.tilResume.text
            val description = binding.tilDescription.text
            val status = binding.cbStatus.isChecked
            val id = intent.getIntExtra(TASK_ID, 0)


            if (title.isNotEmpty() && date.isNotEmpty()) {
                val task = Task(title, date, time, resume, description, status, id)

                if (intent.hasExtra(TASK_ID)) {
                    thread(true) {
                        viewModel.update(task)
                    }
                    Toast.makeText(this, getString(R.string.update), Toast.LENGTH_LONG).show()
                } else {

                    thread(true) {
                        viewModel.insert(task)
                    }
                    Toast.makeText(this@AddTaskActivity, getString(R.string.sucess), Toast.LENGTH_LONG).show()
                }

                setResult(Activity.RESULT_OK)
                finish()

            } else {

                Toast.makeText(
                    this@AddTaskActivity,
                    getString(R.string.required_fields),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.btnCancel.setOnClickListener { finish() }

    }

    companion object {
        const val TASK_ID = "task_id"
    }
}