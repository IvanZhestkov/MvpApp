package com.itis.android.mvpapp.presentation.ui.student

import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskFragment
import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskModule
import com.itis.android.mvpapp.presentation.ui.student.loadtask.LoadStudentTaskScreen
import com.itis.android.mvpapp.presentation.ui.student.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.student.profile.ProfileModule
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksFragment
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksModule
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListFragment
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListModule
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogFragment
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StudentBuilder {

    @ContributesAndroidInjector(modules = [StudentTasksModule::class])
    abstract fun buildStudentTasksFragment(): StudentTasksFragment

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun buildProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileModule::class])
    abstract fun buildTeacherProfileFragment(): com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileFragment

    @ContributesAndroidInjector(modules = [LoadStudentTaskModule::class])
    abstract fun buildLoadStudentTaskFragment(): LoadStudentTaskFragment

    @ContributesAndroidInjector(modules = [DialogListModule::class])
    abstract fun buildDialogListFragment(): DialogListFragment

    @ContributesAndroidInjector(modules = [DialogModule::class])
    abstract fun buildDialogFragment(): DialogFragment
}