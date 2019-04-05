package com.itis.android.mvpapp.presentation

import com.itis.android.mvpapp.presentation.ui.auth.AuthQualifier
import com.itis.android.mvpapp.presentation.ui.main.MainQualifier
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.MainRouterImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
class CiceroneRouterModule {

    @Provides
    @AuthQualifier
    @Singleton
    fun provideAuthRouter(): Router = MainRouterImpl()

    @Provides
    @MainQualifier
    @Singleton
    fun provideMainRouter(): Router = MainRouterImpl()
}