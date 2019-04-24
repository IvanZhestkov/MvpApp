package com.itis.android.mvpapp.data.repository.impl

import com.itis.android.mvpapp.data.repository.GroupListRepository
import com.itis.android.mvpapp.model.Group
import javax.inject.Inject

class GroupListRepositoryImpl
@Inject constructor() : GroupListRepository {

    private val groups: List<Group> = listOf(
            Group(0, "11-601"),
            Group(1, "11-602"),
            Group(2, "11-603"),
            Group(3, "11-604"),
            Group(4, "11-605"),
            Group(5, "11-606"),
            Group(6, "11-607"),
            Group(7, "11-608")
    )

    override fun getGroups(): List<Group> = groups

    override fun getGroupById(groupId: Int): Group = groups[groupId]
}