package com.itis.android.mvpapp.data.network.pojo.firebase.request

data class UploadTaskItem(
    val name: String? = null,
    val description: String? = null,
    val expiration_date: String? = null,
    val uploaded_date: String? = null,
    val filePath: String? = null
)