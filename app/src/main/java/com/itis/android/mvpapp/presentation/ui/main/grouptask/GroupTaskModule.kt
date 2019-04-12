package com.itis.android.mvpapp.presentation.ui.main.grouptask

import android.content.Context
import com.itis.android.mvpapp.presentation.adapter.GroupTaskTableAdapter
import dagger.Module
import dagger.Provides

@Module
class GroupTaskModule {

    @Provides
    fun provideTableAdapter(context: Context): GroupTaskTableAdapter = GroupTaskTableAdapter(context)
}