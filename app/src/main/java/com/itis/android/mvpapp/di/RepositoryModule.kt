package com.itis.android.mvpapp.di

import android.content.Context
import com.itis.android.mvpapp.repository.AuthRepository
import com.itis.android.mvpapp.repository.AuthRepositoryImpl
import com.itis.android.mvpapp.utils.AppPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun authRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Singleton
    @Provides
    fun preferences(context: Context): AppPreferences {
        return AppPreferences(context)
    }
}