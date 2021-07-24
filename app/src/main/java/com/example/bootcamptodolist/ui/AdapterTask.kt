package com.example.bootcamptodolist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bootcamptodolist.R
import com.example.bootcamptodolist.databinding.ItemTaskBinding
import com.example.bootcamptodolist.model.Task

class AdapterTask : RecyclerView.Adapter<AdapterTask.ViewHolderTask>() {

    private var listTask = listOf<Task>()

    var listenerEdit: (Task) -> Unit = {}
    var listenerDelete: (Task) -> Unit = {}

    fun setData(listItem: List<Task>) {
        listTask = listItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTask {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return ViewHolderTask(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderTask, position: Int) {
        holder.bind(listTask[position])
    }

    override fun getItemCount(): Int = listTask.size

    inner class ViewHolderTask(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.tvTitle.text = task.title
            binding.tvDateTime.text = "${task.date} ${task.time}"
            binding.tvResume.text = task.resume
            binding.ivMore.setOnClickListener { showPopUp(task) }
        }

        private fun showPopUp(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.action_edit -> {
                        listenerEdit(item)
                    }

                    R.id.action_delete -> listenerDelete(item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }
}