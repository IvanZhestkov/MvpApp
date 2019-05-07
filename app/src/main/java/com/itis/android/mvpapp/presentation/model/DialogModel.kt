package com.itis.android.mvpapp.presentation.model

import java.io.Serializable

data class DialogModel(
        var dialogName: String? = null,
        var image: Int? = null,
        var lastMessage: TextMessageModel? = null
) : Serializable