package com.itis.android.mvpapp.presentation.util.extensions

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns


fun Uri.getFilename(context: Context?): String {
    var cursor: Cursor? = null

    var displayName = ""
    cursor = context?.contentResolver?.query(this, null, null, null, null)
    if (cursor != null && cursor.moveToFirst()) {
        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    cursor?.close()

    return displayName
}

fun getFileName(): String {
    val a = (Math.random() * 1000).toInt()
    val b = (Math.random() * 1000).toInt()
    val c = (Math.random() * 1000).toInt()
    val d = (Math.random() * 1000).toInt()

    return "file$a$b$c$d"
}