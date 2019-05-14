package com.itis.android.mvpapp.presentation.ui.student

import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskFragment
import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskModule
import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskScreen
import com.itis.android.mvpapp.presentation.ui.student.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.student.profile.ProfileModule
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksFragment
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StudentBuilder {

    @ContributesAndroidInjector(modules = [StudentTasksModule::class])
    abstract fun buildStudentTasksFragment(): StudentTasksFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun buildProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [LoadStudentTaskModule::class])
    abstract fun buildLoadStudentTaskFragment(): LoadStudentTaskFragment
}