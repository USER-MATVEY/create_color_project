package com.dobriyanovmp.create_color_app

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class activityState(
    var viewColor: Int
    ): Parcelable