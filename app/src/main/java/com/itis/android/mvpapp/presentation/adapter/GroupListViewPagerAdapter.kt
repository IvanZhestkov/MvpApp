package com.itis.android.mvpapp.presentation.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.itis.android.mvpapp.model.Group
import com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks.TasksFragment
import com.itis.android.mvpapp.router.initparams.TasksInitParams

class GroupListViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val groups: MutableList<Group> = ArrayList()

    override fun getItem(position: Int): Fragment {
        val groupId = groups[position].id
        return TasksFragment.getInstance(TasksInitParams(groupId))
    }

    override fun getCount(): Int {
        return groups.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return groups[position].name
    }

    fun addGroups(groups: List<Group>) {
        this.groups.clear()
        this.groups.addAll(groups)
    }
}