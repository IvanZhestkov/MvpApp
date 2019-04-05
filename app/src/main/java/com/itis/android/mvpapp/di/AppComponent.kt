package com.itis.android.mvpapp.di

import android.app.Application
import android.app.Presentation
import com.itis.android.mvpapp.MvpApp
import com.itis.android.mvpapp.data.DataModule
import com.itis.android.mvpapp.di.app.AppModule
import com.itis.android.mvpapp.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class,
            PresentationModule::class,
            DataModule::class
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