package com.daking.lottery.dialog.nice

import android.os.Parcel
import android.os.Parcelable
import android.view.View

abstract class ViewConvertListener constructor(parcel: Parcel? = null) : Parcelable {

    override fun writeToParcel(p0: Parcel?, p1: Int) {
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<ViewConvertListener> {
        override fun createFromParcel(parcel: Parcel): ViewConvertListener {
            return object : ViewConvertListener(parcel) {
                override fun convertView(view: View, dialog: BaseDialog) {

                }
            }
        }

        override fun newArray(size: Int): Array<ViewConvertListener?> {
            return arrayOfNulls(size)
        }
    }

    abstract fun convertView(view: View, dialog: BaseDialog)
}