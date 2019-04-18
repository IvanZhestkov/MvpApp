package com.itis.android.mvpapp.router.initparams

import android.os.Parcel
import android.os.Parcelable

class TasksInitParams(
        val groupId: Int
) : InitParams {
    constructor(parcel: Parcel) : this(
            parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(groupId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TasksInitParams> {
        override fun createFromParcel(parcel: Parcel): TasksInitParams {
            return TasksInitParams(parcel)
        }

        override fun newArray(size: Int): Array<TasksInitParams?> {
            return arrayOfNulls(size)
        }
    }
}