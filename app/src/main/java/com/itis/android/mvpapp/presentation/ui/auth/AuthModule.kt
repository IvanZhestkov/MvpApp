package com.itis.android.mvpapp.presentation.ui.auth

import com.itis.android.mvpapp.router.AuthRouter
import com.itis.android.mvpapp.router.AuthRouterImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

@Module(includes = [AuthBuilder::class])
class AuthModule {

    private val authRouter = AuthRouterImpl()

    private val cicerone = Cicerone.create(authRouter)

    private val holder = cicerone.navigatorHolder

    @Provides
    fun provideMainRouter(): AuthRouter = authRouter

    @Provides
    fun provideCiceroneNavigatorHolder(): NavigatorHolder = holder

    @Provides
    fun provideCiceroneNavigator(activity: AuthActivity): Navigator = SupportAppNavigator(
        activity, activity.supportFragmentManager, activity.fragmentContainer
    )
}