package com.itis.android.mvpapp.presentation.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.ui.main.groups.tasks.TasksFragment

class GroupListViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var groups: MutableList<GroupModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment {
        return TasksFragment.getInstance(groups[position])
    }

    override fun getCount(): Int {
        return groups.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return groups[position].name
    }
}