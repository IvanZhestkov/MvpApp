package com.itis.android.mvpapp.presentation.ui.student.tasks

import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks.TasksFragment
import dagger.Module
import dagger.Provides

@Module
class StudentTasksModule {

    @Provides
    fun getGroup(fragment: StudentTasksFragment): TaskModel = TODO()


    @Provides
    fun provideAdapter(): TasksAdapter = TasksAdapter()
}