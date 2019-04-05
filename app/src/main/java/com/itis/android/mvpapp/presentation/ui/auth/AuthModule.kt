package com.itis.android.mvpapp.presentation.ui.auth

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module(includes = [AuthBuilder::class])
class AuthModule {

    @Provides
    @AuthQualifier
    fun provideCicerone(@AuthQualifier router: Router): Cicerone<Router> = Cicerone.create(router)

    @Provides
    @AuthQualifier
    fun provideCiceroneNavigatorHolder(@AuthQualifier cicerone: Cicerone<Router>): NavigatorHolder = cicerone.navigatorHolder
}