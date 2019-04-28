package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.pojo.TaskItem
import com.itis.android.mvpapp.presentation.model.TaskModel
import kotlinx.android.synthetic.main.item_task.view.*

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    var items: MutableList<TaskModel> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClickListener: ((TaskModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TasksViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val item = items[adapterPosition]

            tv_deadline.text = item.expiration_date
            tv_subject.text = item.disciplineName
            tv_task_description.text = item.description

            itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
        }
    }
}
