package com.itis.android.mvpapp.presentation.ui.main

import com.itis.android.mvpapp.presentation.ui.main.grouplist.GroupListFragment
import com.itis.android.mvpapp.presentation.ui.main.grouplist.GroupListModule
import com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks.TasksFragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskModule
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskFragment
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskModule
import com.itis.android.mvpapp.presentation.ui.main.messages.MessagesFragment
import com.itis.android.mvpapp.presentation.ui.main.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.main.profile.ProfileModule
import com.itis.android.mvpapp.presentation.ui.main.tasksolution.TaskSolutionFragment
import com.itis.android.mvpapp.presentation.ui.main.tasksolution.TaskSolutionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuilder {

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun buildProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [GroupListModule::class])
    abstract fun buildGroupListFragment(): GroupListFragment

    @ContributesAndroidInjector
    abstract fun buildTasksFragment(): TasksFragment

    @ContributesAndroidInjector(modules = [LoadTaskModule::class])
    abstract fun buildLoadTaskFragment(): LoadTaskFragment

    @ContributesAndroidInjector(modules = [GroupTaskModule::class])
    abstract fun buildGroupTaskFragment(): GroupTaskFragment

    @ContributesAndroidInjector(modules = [TaskSolutionModule::class])
    abstract fun buildTaskSolutionFragment(): TaskSolutionFragment

    @ContributesAndroidInjector
    abstract fun buildMessagesFragment(): MessagesFragment
}