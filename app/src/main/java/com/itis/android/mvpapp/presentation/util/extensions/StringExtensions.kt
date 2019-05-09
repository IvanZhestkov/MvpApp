package com.itis.android.mvpapp.presentation.util.extensions

fun String.getDialogAvatar(): String {
    return "${this[0].toUpperCase()}${this.substringAfter(" ")[0]}".toUpperCase()
}