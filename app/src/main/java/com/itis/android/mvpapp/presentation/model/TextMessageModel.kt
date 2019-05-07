package com.itis.android.mvpapp.presentation.model

import java.io.Serializable

data class TextMessageModel(
        var text: String? = null,
        var dateSend: String? = null,
        var from: String? = null,
        var to: String? = null) : Serializable