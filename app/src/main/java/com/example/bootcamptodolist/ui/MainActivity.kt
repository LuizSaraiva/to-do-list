package com.example.bootcamptodolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.bootcamptodolist.R
import com.example.bootcamptodolist.databinding.ActivityMainBinding
import com.example.bootcamptodolist.application.ApplicationTask
import com.example.bootcamptodolist.viewmodel.ViewModelTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: ViewModelTask by lazy {
        ViewModelTask((application as ApplicationTask).repository)
    }

    lateinit var adapterTask:AdapterTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterTask = AdapterTask()
        binding.rvMain.adapter = adapterTask

        insertListeners()

        loadList()

    }

    private fun insertListeners() {

        adapterTask.listenerEdit = {

            val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivity(intent)
        }

        adapterTask.listenerDelete = {
            Thread {
                viewModel.delete(it)
            }.start()
            Toast.makeText(this, getString(R.string.item_delete), Toast.LENGTH_LONG).show()
            loadList()
        }

        binding.fab.setOnClickListener {
            startActivityForResult(
                Intent(this@MainActivity, AddTaskActivity::class.java),
                RESULT_NEW_TASK
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_NEW_TASK && resultCode == Activity.RESULT_OK) loadList()
    }

    private fun loadList() {
        viewModel.listTask.observe(this) {

            it?.let {
                binding.includeEmptyState.emptyState.visibility = if (it.isEmpty())
                    View.VISIBLE else View.GONE

                binding.rvMain.visibility = if (it.isEmpty())
                    View.GONE else View.VISIBLE

                adapterTask.setData(it)

            }
            adapterTask.notifyDataSetChanged ()
        }
    }

    companion object {
        private const val RESULT_NEW_TASK = 100
    }
}