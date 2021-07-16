package com.example.bootcamptodolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bootcamptodolist.databinding.ActivityMainBinding
import com.example.bootcamptodolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter: AdapterTask by lazy { AdapterTask() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMain.adapter = adapter
        loadList()

        insertListeners()
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
                TaskDataSource.deleteTask(it)
                loadList()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_NEW_TASK && resultCode == Activity.RESULT_OK) loadList()
    }

    private fun loadList() {
        val list = TaskDataSource.getList()
        binding.includeEmptyState.emptyState.visibility = if (list.isEmpty())
             View.VISIBLE else View.GONE

        binding.rvMain.visibility = if (list.isEmpty())
            View.GONE else View.VISIBLE

        adapter.submitList(list)
    }

    companion object {
        private const val RESULT_NEW_TASK = 100
    }
}
