package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.Group


interface GroupListRepository {

    fun getGroups(): List<Group>

    fun getGroupById(groupId: Int): Group
}