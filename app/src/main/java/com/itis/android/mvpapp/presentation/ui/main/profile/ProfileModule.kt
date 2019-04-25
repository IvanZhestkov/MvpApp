package com.itis.android.mvpapp.presentation.ui.main.profile

import com.itis.android.mvpapp.presentation.adapter.DisciplineAdapter
import dagger.Module
import dagger.Provides

@Module
class ProfileModule {

    @Provides
    fun provideAdapter() = DisciplineAdapter()
}