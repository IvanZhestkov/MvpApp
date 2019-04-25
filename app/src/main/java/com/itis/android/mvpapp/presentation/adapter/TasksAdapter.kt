package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter(
        private val onItemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    private val items: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(viewHolder: TasksViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(models: List<Task>) {
        clearItems()
        items.addAll(models)
        notifyDataSetChanged()
    }

    private fun clearItems() {
        items.clear()
    }

    inner class TasksViewHolder(itemView: View, private val onClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val item = items[adapterPosition]

            tv_deadline.text = item.expiration_date
            tv_subject.text = item.subject
            tv_task_description.text = item.description

            itemView.setOnClickListener {
                onClickListener.invoke(adapterPosition)
            }
        }
    }
}
