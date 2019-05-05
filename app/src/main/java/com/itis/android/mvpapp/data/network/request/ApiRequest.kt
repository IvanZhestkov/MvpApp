package com.itis.android.mvpapp.data.network.request

import com.itis.android.mvpapp.data.network.pojo.studapi.request.UploadTaskRequest
import io.reactivex.Completable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiRequest {

    @Multipart
    @POST("/storage/task/upload")
    fun uploadTaskCompletable(
            @Part file: MultipartBody.Part,
            @Part("courseId") courseId: RequestBody,
            @Part("taskId") taskId: RequestBody,
            @Part("idToken") idToken: RequestBody
    ): Completable
}