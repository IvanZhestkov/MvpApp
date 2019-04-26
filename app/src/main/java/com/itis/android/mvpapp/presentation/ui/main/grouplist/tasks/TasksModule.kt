package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.model.TaskModel
import dagger.Module
import dagger.Provides

@Module
class TasksModule {

    @Provides
    fun getTasks(fragment: TasksFragment): ArrayList<TaskModel> = fragment.getTasks()


    @Provides
    fun provideAdapter(): TasksAdapter = TasksAdapter()
}