package com.itis.android.mvpapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.Discipline
import kotlinx.android.synthetic.main.item_discipline.view.*

class DisciplineAdapter : RecyclerView.Adapter<DisciplineAdapter.DisciplineViewHolder>() {

    private val items: MutableList<Discipline> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_discipline, parent, false)
        return DisciplineViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: DisciplineViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(models: List<Discipline>) {
        clearItems()
        items.addAll(models)
        notifyDataSetChanged()
    }

    private fun clearItems() {
        items.clear()
    }

    inner class DisciplineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() = with(itemView) {
            val item = items[adapterPosition]

            name.text = itemView.resources.getString(
                    R.string.name_discipline_format, item.name)

            list_group.text = item.groups.joinToString { it.toString() }
        }
    }
}
