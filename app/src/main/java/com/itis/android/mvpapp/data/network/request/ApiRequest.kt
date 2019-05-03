package com.itis.android.mvpapp.data.network.request

import com.itis.android.mvpapp.data.network.pojo.studapi.request.UploadTaskItem
import io.reactivex.Completable
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiRequest {

    @Multipart
    @POST("/task/upload")
    fun uploadTaskCompletable(@Part file: MultipartBody.Part, @Body uploadTask: UploadTaskItem): Completable
}