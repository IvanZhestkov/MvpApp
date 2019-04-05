package com.itis.android.mvpapp.data

import com.itis.android.mvpapp.data.repository.AuthRepository
import com.itis.android.mvpapp.data.repository.impl.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBuilder {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}