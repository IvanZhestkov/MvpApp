package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListView
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class DialogListPresenter @Inject constructor() : BasePresenter<DialogListView>() {

    @Inject
    lateinit var router: MainRouter

    fun onDialog(dialog: DialogModel) {
        router.openDialogScreen(dialog)
    }
}