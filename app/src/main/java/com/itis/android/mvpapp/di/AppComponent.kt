package com.itis.android.mvpapp.di

import android.app.Application
import com.itis.android.mvpapp.MvpApp
import com.itis.android.mvpapp.api.ApiModule
import com.itis.android.mvpapp.router.RouterModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            AppBuildersModule::class,
            RouterModule::class,
            ApiModule::class,
            RepositoryModule::class
        ]
)
@Singleton
interface AppComponent : AndroidInjector<MvpApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MvpApp>() {

        @BindsInstance
        abstract fun application(application: Application): Builder
    }
}