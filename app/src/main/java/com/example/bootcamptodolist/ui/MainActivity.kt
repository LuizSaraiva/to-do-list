package com.example.bootcamptodolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bootcamptodolist.database.DatabaseTask
import com.example.bootcamptodolist.databinding.ActivityMainBinding
import com.example.bootcamptodolist.datasource.TaskDataSource
import com.example.bootcamptodolist.model.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter: AdapterTask by lazy { AdapterTask() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.adapter = adapter

        insertListeners()

        loadList()

    }

    private fun insertListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(
                Intent(this@MainActivity, AddTaskActivity::class.java),
                RESULT_NEW_TASK
            )

            adapter.listenerEdit = {

                val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
                intent.putExtra(AddTaskActivity.TASK_ID, it.id)
                startActivity(intent)
            }

            adapter.listenerDelete = {

                loadList()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_NEW_TASK && resultCode == Activity.RESULT_OK) loadList()
    }

    private fun loadList() {
        Thread {
            val list = TaskDataSource.getList()

            //val list = DatabaseTask.getDatabase(this@MainActivity)?.taskDao()?.getAll() ?: mutableListOf()

            runOnUiThread {
                binding.includeEmptyState.emptyState.visibility = if (list.isEmpty())
                    View.VISIBLE else View.GONE

                binding.rvMain.visibility = if (list.isEmpty())
                    View.GONE else View.VISIBLE

                adapter.submitList(list)
            }
        }.start()
    }

    companion object {
        private const val RESULT_NEW_TASK = 100
    }
}
