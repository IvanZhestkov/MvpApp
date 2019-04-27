package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.GroupModel
import io.reactivex.Single

interface GroupsRepository {

    fun getGroupsSingle(): Single<List<GroupModel>>
}