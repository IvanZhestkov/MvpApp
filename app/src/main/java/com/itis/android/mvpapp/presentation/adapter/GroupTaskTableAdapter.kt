package com.itis.android.mvpapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskCell
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskColumnHeader
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskRowHeader
import kotlinx.android.synthetic.main.item_croup_task_cell_mark.view.*
import kotlinx.android.synthetic.main.item_group_task_cell_answer.view.*
import kotlinx.android.synthetic.main.item_group_task_column_header.view.*
import kotlinx.android.synthetic.main.item_group_task_row_header.view.*
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.utils.extensions.setTextStyle

class GroupTaskTableAdapter(val context: Context) : AbstractTableAdapter<GroupTaskColumnHeader, GroupTaskRowHeader, GroupTaskCell>(context) {

    companion object {
        const val COLUMN_ANSWER = 0
        private const val COLUMN_MARK = 1

        private const val VIEW_ANSWER = 10
        private const val VIEW_MARK = 20
    }

    override fun onCreateColumnHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.item_group_task_column_header, parent, false)
        return ColumnHeaderViewHolder(v)
    }

    override fun onBindColumnHeaderViewHolder(holder: AbstractViewHolder?, columnHeaderItemModel: Any?, columnPosition: Int) {
        (holder as? ColumnHeaderViewHolder)?.bindView((columnHeaderItemModel as? GroupTaskColumnHeader))
    }

    override fun getColumnHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateRowHeaderViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val v = LayoutInflater
                .from(parent?.context)
                .inflate(R.layout.item_group_task_row_header, parent, false)
        return RowHeaderViewHolder(v)
    }

    override fun onBindRowHeaderViewHolder(holder: AbstractViewHolder?, rowHeaderItemModel: Any?, rowPosition: Int) {
        (holder as? RowHeaderViewHolder)?.bindView((rowHeaderItemModel as? GroupTaskRowHeader))
    }

    override fun getRowHeaderItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateCellViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        return when (viewType) {
            VIEW_ANSWER -> {
                val v = LayoutInflater
                        .from(parent?.context)
                        .inflate(R.layout.item_group_task_cell_answer, parent, false)
                CellAnswerViewHolder(v)
            }
            else -> {
                val v = LayoutInflater
                        .from(parent?.context)
                        .inflate(R.layout.item_croup_task_cell_mark, parent, false)
                CellMarkViewHolder(v)
            }
        }

    }

    override fun onBindCellViewHolder(holder: AbstractViewHolder?, cellItemModel: Any?, columnPosition: Int, rowPosition: Int) {
        val item = (cellItemModel as? GroupTaskCell)

        when (holder?.itemViewType) {
            VIEW_ANSWER -> (holder as? CellAnswerViewHolder)?.bindView(item, rowPosition, columnPosition)
            else -> (holder as? CellMarkViewHolder)?.bindView(item, rowPosition, columnPosition)
        }
    }

    override fun getCellItemViewType(column: Int): Int {
        return when (column) {
            COLUMN_ANSWER -> VIEW_ANSWER
            else -> VIEW_MARK
        }
    }

    override fun onCreateCornerView(): View {
        return LayoutInflater.from(context).inflate(R.layout.item_group_task_corner, null)
    }


    inner class ColumnHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {

        fun bindView(item: GroupTaskColumnHeader?) = with(itemView) {
            header_column_text.text = item?.text
        }
    }

    inner class RowHeaderViewHolder(itemView: View) : AbstractViewHolder(itemView) {

        fun bindView(item: GroupTaskRowHeader?) = with(itemView) {
            header_row_text.text = item?.text
        }
    }

    inner class CellAnswerViewHolder(itemView: View) : AbstractViewHolder(itemView) {

        var cellItem: GroupTaskCell? = null

        fun bindView(item: GroupTaskCell?, row: Int, column: Int) = with(itemView) {
            cellItem = item
            val rand = (Math.random() * 2).toInt()

            if (rand == 1) {
                cell_answer_text.text = context.getString(R.string.task_status_show)
                cell_answer_text.setTextStyle(context, R.style.Text_Small_Link)
            } else {
                cell_answer_text.text = context.getString(R.string.task_status_now_downloaded)
                cell_answer_text.setTextStyle(context, R.style.Text_Small_Disable)
            }
        }
    }

    inner class CellMarkViewHolder(itemView: View) : AbstractViewHolder(itemView) {

        fun bindView(item: GroupTaskCell?, row: Int, column: Int) = with(itemView) {
            val rand = (Math.random() * 2).toInt()

            image_group_task_mark.setImageResource(
                    if (rand == 1) R.drawable.ic_check_mark_done
                    else R.drawable.ic_check_mark_close
            )
        }
    }
}