package com.sunday.shiftsapplication.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Calendar.format(): String{
    val dateFormat = SimpleDateFormat("EEE, MMM d h:mm a")
    return dateFormat.format(this.time)
}