package com.itis.android.mvpapp.presentation.ui.student

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
}