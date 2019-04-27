package com.itis.android.mvpapp.presentation.ui.teacher

import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.MainRouterImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator

@Module(includes = [TeacherBuilder::class])
class TeacherModule {

    private val mainRouter = MainRouterImpl()

    private val cicerone = Cicerone.create(mainRouter)

    private val holder = cicerone.navigatorHolder

    @Provides
    fun provideMainRouter(): MainRouter = mainRouter

    @Provides
    fun provideCiceroneNavigatorHolder(): NavigatorHolder = holder

    @Provides
    fun provideCiceroneNavigator(activity: TeacherActivity): Navigator = SupportAppNavigator(
        activity, activity.supportFragmentManager, activity.fragmentContainer
    )
}