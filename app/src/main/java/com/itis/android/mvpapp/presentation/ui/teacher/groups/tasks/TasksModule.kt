package com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks

import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.model.GroupModel
import dagger.Module
import dagger.Provides

@Module
class TasksModule {

    @Provides
    fun getGroup(fragment: TasksFragment): GroupModel = fragment.getGroup()


    @Provides
    fun provideAdapter(): TasksAdapter = TasksAdapter()
}