package com.itis.android.mvpapp.presentation.model

data class NewTaskModel(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val deadlineDate: String? = null,
    val discipline: String? = null
)