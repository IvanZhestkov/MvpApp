package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherDisciplineItem
import java.io.Serializable

data class GroupModel(
        val name: String? = null,
        val tasks: List<TaskModel>
) : Serializable

object GroupModelMapper {

    fun map(tasks: List<TaskModel>, disciplines: List<TeacherDisciplineItem>): List<GroupModel> {
        val groupsName = disciplines.map { it.group_id }.distinct().sortedBy { it }

        val groups = ArrayList<GroupModel>()

        groupsName.forEach { group ->
            groups.add(GroupModel(group, tasks.filter { group == it.groupNumber }))
        }

        return groups
    }
}