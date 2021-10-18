package com.sunday.shiftsapplication.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.formatDate(): String{
    val dateFormat = SimpleDateFormat("EEE, MMM d")
    return dateFormat.format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.formatTime (): String{
    val dateFormat = SimpleDateFormat("h:mm a")
    return dateFormat.format(this)
}