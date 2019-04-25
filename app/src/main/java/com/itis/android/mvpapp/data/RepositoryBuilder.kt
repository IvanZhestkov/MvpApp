package com.itis.android.mvpapp.data

import com.itis.android.mvpapp.data.repository.AuthRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.data.repository.GroupListRepository
import com.itis.android.mvpapp.data.repository.impl.AuthRepositoryImpl
import com.itis.android.mvpapp.data.repository.impl.TeacherRepositoryImpl
import com.itis.android.mvpapp.data.repository.impl.GroupListRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBuilder {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindGroupListRepository(repositoryImpl: GroupListRepositoryImpl): GroupListRepository

    @Binds
    @Singleton
    abstract fun bindDisciplineRepository(repositoryImpl: TeacherRepositoryImpl): TeacherRepository
}