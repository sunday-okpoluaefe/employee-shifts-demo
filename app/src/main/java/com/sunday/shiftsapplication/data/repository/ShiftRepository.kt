package com.sunday.shiftsapplication.data.repository

import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.service.ShiftService
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShiftRepository @Inject constructor(private val service: ShiftService): Repository {
    private var shifts: MutableList<Shift> = mutableListOf()

    override suspend fun getShifts(): List<Shift> {
      return withContext(IO){
            if(shifts.isNotEmpty()) shifts
            else {
                shifts = service.getShifts().shifts.toMutableList()
                shifts
            }
        }
    }

    override suspend fun saveShift(shift: Shift) {
        withContext(IO){
            shifts.add(shift)
        }
    }
}