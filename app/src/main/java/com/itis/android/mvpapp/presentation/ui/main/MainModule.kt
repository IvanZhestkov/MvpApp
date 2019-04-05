package com.itis.android.mvpapp.presentation.ui.main

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module(includes = [MainBuilder::class])
class MainModule {

    @Provides
    @MainQualifier
    fun provideCicerone(@MainQualifier router: Router): Cicerone<Router> = Cicerone.create(router)

    @Provides
    @MainQualifier
    fun provideCiceroneNavigatorHolder(@MainQualifier cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder
}