package com.itis.android.mvpapp.data.network.pojo.firebase.response

data class MessageItem(
        var content: String? = null,
        var created_date: Long? = null,
        var from: String? = null
)