package com.itis.android.mvpapp.data.repository.impl

import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherDisciplineItem
import com.itis.android.mvpapp.data.repository.DisciplinesRepository
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.GroupModelMapper
import com.itis.android.mvpapp.presentation.model.TaskModel
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor() : GroupsRepository {

    @Inject
    lateinit var tasksRepository: TasksRepository

    @Inject
    lateinit var disciplinesRepository: DisciplinesRepository

    override fun getGroupsSingle(): Single<List<GroupModel>> {
        return Single.zip(
            tasksRepository.getTasks(),
            disciplinesRepository.getDisciplinesSingle(),
            BiFunction<List<TaskModel>, List<TeacherDisciplineItem>, Pair<List<TaskModel>, List<TeacherDisciplineItem>>>
            { t1, t2 ->
                Pair(t1, t2)
            }).map { (tasks, disciplines) -> GroupModelMapper.map(tasks, disciplines) }
    }
}