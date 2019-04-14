package com.itis.android.mvpapp.presentation.ui.main

import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskModule
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskFragment
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskModule
import com.itis.android.mvpapp.presentation.ui.main.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.main.tasksolution.TaskSolutionFragment
import com.itis.android.mvpapp.presentation.ui.main.tasksolution.TaskSolutionModule
import com.itis.android.mvpapp.presentation.ui.main.test.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuilder {

    @ContributesAndroidInjector()
    abstract fun buildTestFragment(): TestFragment

    @ContributesAndroidInjector()
    abstract fun buildProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [LoadTaskModule::class])
    abstract fun buildLoadTaskFragment(): LoadTaskFragment

    @ContributesAndroidInjector(modules = [GroupTaskModule::class])
    abstract fun buildGroupTaskFragment(): GroupTaskFragment

    @ContributesAndroidInjector(modules = [TaskSolutionModule::class])
    abstract fun buildTaskSolutionFragment(): TaskSolutionFragment
}