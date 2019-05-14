package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.presentation.model.AddMessageModel
import com.itis.android.mvpapp.presentation.model.MessageModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MessagesRepository {

    fun getMessages(chatId: String): Observable<MessageModel>

    fun getLastMessage(chatId: String): Observable<MessageModel>

    fun addMessage(addMessageModel: AddMessageModel): Completable
}