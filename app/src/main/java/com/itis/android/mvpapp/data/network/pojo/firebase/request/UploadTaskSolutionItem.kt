package com.itis.android.mvpapp.data.network.pojo.firebase.request

data class UploadTaskSolutionItem(
        val checking_date: String? = null,
        val commentary: String? = null,
        var score: Int = 0,
        val solution_file_link: String? = null,
        val status: String? = null,
        val uploading_date: Long? = null
)