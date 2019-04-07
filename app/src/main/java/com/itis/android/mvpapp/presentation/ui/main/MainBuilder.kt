package com.itis.android.mvpapp.presentation.ui.main

import com.itis.android.mvpapp.presentation.ui.main.test.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuilder {

    @ContributesAndroidInjector()
    abstract fun buildTestFragment(): TestFragment
}