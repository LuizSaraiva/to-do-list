package com.example.bootcamptodolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.bootcamptodolist.database.DatabaseTask
import com.example.bootcamptodolist.databinding.ActivityMainBinding
import com.example.bootcamptodolist.datasource.TaskDataSource
import com.example.bootcamptodolist.model.Task

class MainActivity : AppCompatActivity() {
    private val taskDataSource by lazy { TaskDataSource(DatabaseTask.getDatabase(this)!!) }
    private lateinit var binding: ActivityMainBinding
    private val adapter: AdapterTask by lazy { AdapterTask() }
    private val registerTaskResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        /**
         * Esse bloco será acionado após a resposta da [AddTaskActivity].
         */
        if (result.resultCode == RESULT_OK) {
            loadList()
        }
    }

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
            // Lançar intenção de criar nova task e aguardar resposta ao final da activity.
            registerTaskResult.launch(Intent(this@MainActivity, AddTaskActivity::class.java))
        }
        adapter.listenerEdit = {
            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivity(intent)
        }
        adapter.listenerDelete = {
            taskDataSource.deleteTask(it)
        }
    }

    // TODO: Remover: onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) foi
    //  depreciado. Veja: https://developer.android.com/training/basics/intents/result
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RESULT_NEW_TASK && resultCode == Activity.RESULT_OK) loadList()
//    }

    private fun loadList() {
        taskDataSource.getList().observe(this) {
            binding.includeEmptyState.emptyState.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            binding.rvMain.visibility = if (it.isEmpty())
                View.GONE else View.VISIBLE
            adapter.submitList(it)
        }
    }
}
