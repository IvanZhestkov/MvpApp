package com.itis.android.mvpapp.data.pojo

import android.os.Parcel
import android.os.Parcelable

data class TaskSolutionItem(
        var id: String? = null,
        var checking_date: String? = null,
        var commentary: String? = null,
        var score: Int = 0,
        var solution_file_link: String? = null,
        var status: String? = null,
        var uploading_date: Long? = null,
        var taskId: String? = null,
        var taskDeadline: String? = null,
        var disciplineId: String? = null,
        var userId: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(checking_date)
        parcel.writeString(commentary)
        parcel.writeInt(score)
        parcel.writeString(solution_file_link)
        parcel.writeString(status)
        uploading_date?.let { parcel.writeLong(it) }
        parcel.writeString(taskId)
        parcel.writeString(taskDeadline)
        parcel.writeString(disciplineId)
        parcel.writeString(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskSolutionItem> {
        override fun createFromParcel(parcel: Parcel): TaskSolutionItem {
            return TaskSolutionItem(parcel)
        }

        override fun newArray(size: Int): Array<TaskSolutionItem?> {
            return arrayOfNulls(size)
        }
    }
}