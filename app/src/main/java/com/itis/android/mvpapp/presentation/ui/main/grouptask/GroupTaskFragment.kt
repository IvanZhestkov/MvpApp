package com.itis.android.mvpapp.presentation.ui.main.grouptask

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.table.grouptask.GroupTaskCell
import com.itis.android.mvpapp.model.table.grouptask.GroupTaskColumnHeader
import com.itis.android.mvpapp.model.table.grouptask.GroupTaskRowHeader
import com.itis.android.mvpapp.presentation.adapter.GroupTaskTableAdapter
import com.itis.android.mvpapp.presentation.adapter.tableClickListener.TableClickListenerAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.utils.extensions.toast
import kotlinx.android.synthetic.main.fragment_group_task.*
import javax.inject.Inject
import javax.inject.Provider

class GroupTaskFragment : BaseFragment(), GroupTaskView {

    companion object {
        fun getInstance() = GroupTaskFragment()
    }

    override val mainContentLayout = R.layout.fragment_group_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: GroupTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupTaskPresenter>

    @Inject
    lateinit var adapter: GroupTaskTableAdapter

    @ProvidePresenter
    fun providePresenter(): GroupTaskPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTable()
    }

    override fun showTable() {
        val columnItems = listOf(
                GroupTaskColumnHeader("Решение"),
                GroupTaskColumnHeader("Оценка")
        )

        val rowItems = listOf(
                GroupTaskRowHeader("Алекбаев Азат"),
                GroupTaskRowHeader("Багаутдинов Роман"),
                GroupTaskRowHeader("Бедрин Олег"),
                GroupTaskRowHeader("Валиев Айзат"),
                GroupTaskRowHeader("Габитова Елизавета"),
                GroupTaskRowHeader("Гаврилов Максим"),
                GroupTaskRowHeader("Гаязов Дамир"),
                GroupTaskRowHeader("Жестков Иван"),
                GroupTaskRowHeader("Дроздов Антон"),
                GroupTaskRowHeader("Гафурова Алина")
        )

        val cellItems = listOf(
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("as"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a")),
                listOf(GroupTaskCell("a"), GroupTaskCell("a"))
        )

        adapter.setAllItems(columnItems, rowItems, cellItems)
    }

    private fun initTable() {
        table_group_task.adapter = adapter
        table_group_task.tableViewListener = object : TableClickListenerAdapter() {
            override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
                when (column) {
                    GroupTaskTableAdapter.COLUMN_ANSWER -> {
                        val item = (cellView as GroupTaskTableAdapter.CellAnswerViewHolder).cellItem
                        if (item?.text == "as") {
                            presenter.openTaskSolutionScreen()
                        }
                    }
                }
            }
        }
    }
}