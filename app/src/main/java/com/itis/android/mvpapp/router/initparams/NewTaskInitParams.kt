package com.itis.android.mvpapp.router.initparams

import android.os.Parcel
import android.os.Parcelable

class NewTaskInitParams(
        val groupName: String?
) : InitParams {
    constructor(parcel: Parcel) : this(
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(groupName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewTaskInitParams> {
        override fun createFromParcel(parcel: Parcel): NewTaskInitParams {
            return NewTaskInitParams(parcel)
        }

        override fun newArray(size: Int): Array<NewTaskInitParams?> {
            return arrayOfNulls(size)
        }
    }
}