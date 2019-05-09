package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import java.util.*

data class MessageModel(
        val content: String? = null,
        val createdDate: Date? = null,
        val messageFrom: MessageFromType
        )

enum class MessageFromType {
    ME, OTHER
}

object MessageModelMapper {
    fun map(messageItem: MessageItem, userId: String): MessageModel {
        return MessageModel(
                messageItem.content,
                Date(messageItem.created_date ?: 0L),
                if(messageItem.from == userId) MessageFromType.ME else MessageFromType.OTHER
        )
    }
}