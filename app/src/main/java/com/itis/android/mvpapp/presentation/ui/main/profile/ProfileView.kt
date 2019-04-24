package com.itis.android.mvpapp.presentation.ui.main.profile

import com.arellomobile.mvp.MvpView
import com.itis.android.mvpapp.model.Discipline
import com.itis.android.mvpapp.model.User

interface ProfileView: MvpView {

    fun showProfile(user: User?)

    fun showDisciplines(items: List<Discipline>)
}