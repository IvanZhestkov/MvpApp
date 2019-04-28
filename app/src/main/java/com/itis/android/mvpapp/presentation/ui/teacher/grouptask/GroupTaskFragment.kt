package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskCell
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskColumnHeader
import com.itis.android.mvpapp.presentation.model.table.grouptask.GroupTaskRowHeader
import com.itis.android.mvpapp.presentation.adapter.GroupTaskTableAdapter
import com.itis.android.mvpapp.presentation.adapter.tableClickListener.TableClickListenerAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.TaskSolutionModel
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.putInitParams
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import kotlinx.android.synthetic.main.fragment_group_task.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class GroupTaskFragment : BaseFragment(), GroupTaskView {

    companion object {
        fun getInstance(initParams: GroupTaskInitParams): GroupTaskFragment {
            return GroupTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
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

    var adapter: GroupTaskTableAdapter? = null

    @ProvidePresenter
    fun providePresenter(): GroupTaskPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<GroupTaskInitParams>().task)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTable()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }

        progress_error.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }

    override fun showTable(solutions: List<TaskSolutionModel>) {
        val columnItems = mutableListOf(
                GroupTaskColumnHeader("Решение"),
                GroupTaskColumnHeader("Оценка")
        )

        val rowItems: MutableList<GroupTaskRowHeader> = mutableListOf(
                /* GroupTaskRowHeader("Алекбаев Азат"),
                 GroupTaskRowHeader("Багаутдинов Роман"),
                 GroupTaskRowHeader("Бедрин Олег"),
                 GroupTaskRowHeader("Валиев Айзат"),
                 GroupTaskRowHeader("Габитова Елизавета"),
                 GroupTaskRowHeader("Гаврилов Максим"),
                 GroupTaskRowHeader("Гаязов Дамир"),
                 GroupTaskRowHeader("Жестков Иван"),
                 GroupTaskRowHeader("Дроздов Антон"),
                 GroupTaskRowHeader("Гафурова Алина")*/
        )

        solutions.forEach { solution ->
            rowItems.add(GroupTaskRowHeader("${solution.user?.last_name} ${solution.user?.first_name}"))
        }

        val cellItems = mutableListOf(
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a"))
                /*mutableListOf(GroupTaskCell("as"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a")),
                mutableListOf(GroupTaskCell("a"), GroupTaskCell("a"))*/
        )


        adapter?.setAllItems(columnItems, rowItems, cellItems)
    }

    private fun initTable() {
        context?.let { adapter = GroupTaskTableAdapter(it) }
        table_group_task.adapter = adapter
        table_group_task.tableViewListener = object : TableClickListenerAdapter() {
            override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
                when (column) {
                    GroupTaskTableAdapter.COLUMN_ANSWER -> {
                        val item = (cellView as GroupTaskTableAdapter.CellAnswerViewHolder).cellItem
                        presenter.openTaskSolutionScreen()
                    }
                }
            }
        }
    }
}