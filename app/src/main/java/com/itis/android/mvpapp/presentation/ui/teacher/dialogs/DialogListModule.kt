package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import com.itis.android.mvpapp.presentation.adapter.DialogListAdapter
import dagger.Module
import dagger.Provides

@Module
class DialogListModule {

    @Provides
    fun provideAdapter() = DialogListAdapter()
}