package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.pojo.TaskItem
import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import java.io.Serializable

data class TaskModel(
        var expiration_date: String? = null,
        var description: String? = null,
        var subject: String? = null,
        var name: String? = null,
        var taskId: String? = null,
        var disciplineId: String? = null,
        var groupNumber: String? = null,
        var disciplineName: String? = null,
        var professorId: String? = null
): Serializable

object  TaskModelMapper {
    fun map(taskItem: TaskItem, disciplineItem: TeacherDisciplineItem): TaskModel {
        return TaskModel(
                taskItem.expiration_date,
                taskItem.description,
                taskItem.subject,
                taskItem.name,
                taskItem.taskId,
                taskItem.disciplineId,
                disciplineItem.group_id,
                disciplineItem.subject_name,
                disciplineItem.professor_id
        )
    }
}