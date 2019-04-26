package com.itis.android.mvpapp.data

import com.itis.android.mvpapp.data.repository.*
import com.itis.android.mvpapp.data.repository.impl.*
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
    abstract fun bindLoginRepository(repositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindGroupListRepository(repositoryImpl: GroupListRepositoryImpl): GroupListRepository

    @Binds
    @Singleton
    abstract fun bindDisciplineRepository(repositoryImpl: TeacherRepositoryImpl): TeacherRepository

    @Binds
    @Singleton
    abstract fun bindTasksRepository(repositoryImpl: TasksRepositoryImpl): TasksRepository

    @Binds
    @Singleton
    abstract fun bindDisciplinesRepository(repositoryImpl: DisciplinesRepositoryImpl): DisciplinesRepository
}