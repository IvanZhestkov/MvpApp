package com.itis.android.mvpapp.router.initparams

import android.os.Parcel
import android.os.Parcelable
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel

class TaskSolutionInitParams(
        val userSolution: UserSolutionModel

) : InitParams {
    constructor(parcel: Parcel) : this(
            parcel.readSerializable() as UserSolutionModel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(userSolution)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskSolutionInitParams> {
        override fun createFromParcel(parcel: Parcel): TaskSolutionInitParams {
            return TaskSolutionInitParams(parcel)
        }

        override fun newArray(size: Int): Array<TaskSolutionInitParams?> {
            return arrayOfNulls(size)
        }
    }
}