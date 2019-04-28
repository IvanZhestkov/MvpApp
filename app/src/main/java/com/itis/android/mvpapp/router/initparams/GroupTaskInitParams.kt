package com.itis.android.mvpapp.router.initparams

import android.os.Parcel
import android.os.Parcelable
import com.itis.android.mvpapp.presentation.model.TaskModel

class GroupTaskInitParams(
        val task: TaskModel

) : InitParams {
    constructor(parcel: Parcel) : this(
            parcel.readSerializable() as TaskModel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(task)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupTaskInitParams> {
        override fun createFromParcel(parcel: Parcel): GroupTaskInitParams {
            return GroupTaskInitParams(parcel)
        }

        override fun newArray(size: Int): Array<GroupTaskInitParams?> {
            return arrayOfNulls(size)
        }
    }
}