package com.sunday.shiftsapplication.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Shift(
val role: String,
val name: String,
@SerializedName("start_date")
val startDate: Date,
@SerializedName("end_date")
val endDate: Date,
val color: String
) : Parcelable