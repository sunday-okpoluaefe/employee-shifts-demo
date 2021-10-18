package com.sunday.shiftsapplication.data.repository

import com.sunday.shiftsapplication.data.model.Shift

interface Repository {
    suspend fun getShifts(): List<Shift>
    suspend fun saveShift(shift: Shift)
}