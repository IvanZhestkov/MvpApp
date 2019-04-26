package com.itis.android.mvpapp.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.itis.android.mvpapp.presentation.model.Group
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks.TasksFragment
import com.itis.android.mvpapp.router.initparams.TasksInitParams

class GroupListViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var groups: MutableList<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var tasks: MutableList<TaskModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        val groupName = groups[position]
        return TasksFragment.getInstance(ArrayList(tasks.filter { it.groupNumber == groupName }))
    }

    override fun getCount(): Int {
        return groups.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return groups[position]
    }
}