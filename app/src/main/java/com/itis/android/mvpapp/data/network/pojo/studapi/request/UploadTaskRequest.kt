package com.itis.android.mvpapp.data.network.pojo.studapi.request

import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class UploadTaskRequest(
        val courseID: String? = null,
        val taskID: String? = null,
        val idToken: String? = null
)