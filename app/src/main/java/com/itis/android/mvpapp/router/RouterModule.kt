package com.itis.android.mvpapp.router

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Singleton

@Module
class RouterModule {

    private val mainRouter = MainRouterImpl()

    private val cicerone = Cicerone.create(mainRouter)

    private val holder = cicerone.navigatorHolder

    @Singleton
    @Provides
    fun provideNavigatorHolder() = holder

    @Singleton
    @Provides
    fun provideMainRouter(): MainRouter = mainRouter
}