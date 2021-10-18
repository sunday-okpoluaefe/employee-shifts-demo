package com.sunday.shiftsapplication.service

import android.os.Parcelable
import com.sunday.shiftsapplication.data.model.Shift
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse (val shifts: List<Shift>) : Parcelable