package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import com.itis.android.mvpapp.presentation.ui.auth.AuthModule
import com.itis.android.mvpapp.presentation.ui.student.StudentActivity
import com.itis.android.mvpapp.presentation.ui.student.StudentModule
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherActivity
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RootBuilder {

    @ContributesAndroidInjector(modules = [StudentModule::class])
    abstract fun buildStudentActivity(): StudentActivity

    @ContributesAndroidInjector(modules = [TeacherModule::class])
    abstract fun buildTeacherActivity(): TeacherActivity

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildAuthActivity(): AuthActivity
}