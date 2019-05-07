package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import com.itis.android.mvpapp.presentation.adapter.DialogAdapter
import dagger.Module
import dagger.Provides

@Module
class DialogModule {

    @Provides
    fun provideAdapter() = DialogAdapter()
}