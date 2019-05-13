package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.AddMessageModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationCompletableTransformer
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogView
import javax.inject.Inject

@InjectViewState
class DialogPresenter @Inject constructor() : BasePresenter<DialogView>() {

    @Inject
    lateinit var dialogId: String

    @Inject
    lateinit var messagesRepository: MessagesRepository

    private var message: String? = null

    private var username: String? = null

    fun init(username: String) {
        this.username = username
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkButtonState()
        viewState.showToolbarTitle(username.toString())
        viewState.showProgress()
    }

    override fun attachView(view: DialogView?) {
        super.attachView(view)
        viewState.startListeningAdapter()
    }

    override fun detachView(view: DialogView?) {
        super.detachView(view)
        viewState.stopListeningAdapter()
    }

    fun onAddMessage() {
        messagesRepository
                .addMessage(AddMessageModel(message, dialogId))
                .compose(PresentationCompletableTransformer())
                .subscribe({
                    viewState.clearMessageField()
                }, {
                    viewState.showErrorDialog("Ошибка при отправке сообщения")
                })
                .disposeWhenDestroy()
    }

    fun onMessageChange(message: String) {
        this.message = if (message.isEmpty()) null else message
        checkButtonState()
    }

    private fun checkButtonState() {
        viewState.setButtonEnabled(message != null)
    }

    fun onDataChange(items: MutableList<String>) {
        viewState.setItemDecorationItems(items)
        viewState.hideProgress()
    }
}