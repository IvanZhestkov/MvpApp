package com.itis.android.mvpapp.router.initparams

import android.os.Parcel
import android.os.Parcelable

class TestInitParams(
        val data: String?
) : InitParams {
    constructor(parcel: Parcel) : this(
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TestInitParams> {
        override fun createFromParcel(parcel: Parcel): TestInitParams {
            return TestInitParams(parcel)
        }

        override fun newArray(size: Int): Array<TestInitParams?> {
            return arrayOfNulls(size)
        }
    }
}