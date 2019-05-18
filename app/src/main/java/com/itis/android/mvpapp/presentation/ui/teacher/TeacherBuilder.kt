package com.itis.android.mvpapp.presentation.ui.teacher

import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListFragment
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListModule
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogFragment
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogModule
import com.itis.android.mvpapp.presentation.ui.teacher.groups.GroupsFragment
import com.itis.android.mvpapp.presentation.ui.teacher.groups.GroupsModule
import com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks.TasksFragment
import com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks.TasksModule
import com.itis.android.mvpapp.presentation.ui.teacher.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.presentation.ui.teacher.grouptask.GroupTaskModule
import com.itis.android.mvpapp.presentation.ui.teacher.newtask.NewTaskFragment
import com.itis.android.mvpapp.presentation.ui.teacher.newtask.NewTaskModule
import com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileModule
import com.itis.android.mvpapp.presentation.ui.teacher.tasksolution.TaskSolutionFragment
import com.itis.android.mvpapp.presentation.ui.teacher.tasksolution.TaskSolutionModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TeacherBuilder {

    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun buildProfileFragment(): ProfileFragment

    @ContributesAndroidInjector(modules = [GroupsModule::class])
    abstract fun buildGroupListFragment(): GroupsFragment

    @ContributesAndroidInjector(modules = [TasksModule::class])
    abstract fun buildTasksFragment(): TasksFragment

    @ContributesAndroidInjector(modules = [NewTaskModule::class])
    abstract fun buildNewTaskFragment(): NewTaskFragment

    @ContributesAndroidInjector(modules = [GroupTaskModule::class])
    abstract fun buildGroupTaskFragment(): GroupTaskFragment

    @ContributesAndroidInjector(modules = [TaskSolutionModule::class])
    abstract fun buildTaskSolutionFragment(): TaskSolutionFragment

    @ContributesAndroidInjector(modules = [DialogListModule::class])
    abstract fun buildDialogListFragment(): DialogListFragment

    @ContributesAndroidInjector(modules = [DialogModule::class])
    abstract fun buildDialogFragment(): DialogFragment

    @ContributesAndroidInjector(modules = [com.itis.android.mvpapp.presentation.ui.student.profile.ProfileModule::class])
    abstract fun buildStudentProfileFragment(): com.itis.android.mvpapp.presentation.ui.student.profile.ProfileFragment
}