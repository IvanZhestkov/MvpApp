package com.itis.android.mvpapp.di

import com.itis.android.mvpapp.di.scope.ActivityScope
import com.itis.android.mvpapp.ui.AppActivity
import com.itis.android.mvpapp.ui.AppActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBuildersModule {

    //@ActivityScope
    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    abstract fun appActivityInjector(): AppActivity
}