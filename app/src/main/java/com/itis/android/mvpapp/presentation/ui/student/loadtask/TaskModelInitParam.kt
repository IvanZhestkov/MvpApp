package com.itis.android.mvpapp.presentation.ui.student.loadtask

import android.os.Parcel
import android.os.Parcelable
import com.itis.android.mvpapp.router.initparams.InitParams

class TaskModelInitParam(
    var expiration_date: String? = null,
    var description: String? = null,
    var name: String? = null,
    var filePath: String? = null,
    var taskId: String? = null,
    var disciplineId: String? = null,
    var groupNumber: String? = null,
    var disciplineName: String? = null,
    var professorId: String? = null
) : InitParams {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(expiration_date)
        parcel.writeString(description)
        parcel.writeString(name)
        parcel.writeString(filePath)
        parcel.writeString(taskId)
        parcel.writeString(disciplineId)
        parcel.writeString(groupNumber)
        parcel.writeString(disciplineName)
        parcel.writeString(professorId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModelInitParam> {
        override fun createFromParcel(parcel: Parcel): TaskModelInitParam {
            return TaskModelInitParam(parcel)
        }

        override fun newArray(size: Int): Array<TaskModelInitParam?> {
            return arrayOfNulls(size)
        }
    }
}
