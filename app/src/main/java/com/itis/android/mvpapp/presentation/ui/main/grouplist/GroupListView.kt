package com.itis.android.mvpapp.presentation.ui.main.grouplist

import com.itis.android.mvpapp.presentation.model.Group
import com.itis.android.mvpapp.presentation.base.BaseView

interface GroupListView: BaseView {

    fun setGroups(groups: List<Group>)
}