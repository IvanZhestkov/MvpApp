package com.itis.android.mvpapp.presentation.adapter.tableClickListener

import android.support.v7.widget.RecyclerView
import com.evrencoskun.tableview.listener.ITableViewListener

open class TableClickListenerAdapter : ITableViewListener {
    override fun onCellLongPressed(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}

    override fun onColumnHeaderLongPressed(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}

    override fun onRowHeaderClicked(rowHeaderView: RecyclerView.ViewHolder, row: Int) {}

    override fun onColumnHeaderClicked(columnHeaderView: RecyclerView.ViewHolder, column: Int) {}

    override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {}

    override fun onRowHeaderLongPressed(rowHeaderView: RecyclerView.ViewHolder, row: Int) {}
}