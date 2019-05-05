package com.itis.android.mvpapp.presentation.model

data class UploadTaskModel(
        val name: String? = null,
        val description: String? = null,
        val deadLine: String? = null,
        val group: String? = null,
        val discipline: String? = null,
        val file: FileModel? = null
)