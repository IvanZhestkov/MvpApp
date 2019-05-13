package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import java.io.Serializable
import java.util.*

data class DialogModel(
        var dialogId: String? = null,
        var username: String? = null,
        var lastMessage: String? = null,
        var lastMessageDate: Date? = null,
        var lastMessageFrom: MessageFromType? = null

) : Serializable

object DialogModelMapper {
    fun map(message: MessageModel, user: UserItem, dialogId: String): DialogModel {
        return DialogModel(
                dialogId,
                "${user.last_name} ${user.first_name}",
                message.content,
                message.createdDate,
                message.messageFrom
        )
    }
}