package com.itis.android.mvpapp.presentation.ui.student

import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksFragment
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StudentBuilder {

    @ContributesAndroidInjector(modules = [StudentTasksModule::class])
    abstract fun buildStudentTasksFragment(): StudentTasksFragment;


}