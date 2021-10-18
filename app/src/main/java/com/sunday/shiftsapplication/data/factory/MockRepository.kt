package com.sunday.shiftsapplication.data.factory

import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.data.repository.Repository

class MockRepository: Repository {
    private var shifts: MutableList<Shift> = mutableListOf()

    init {
        shifts = ShiftFactory.shifts().toMutableList()
    }

    override suspend fun getShifts(): List<Shift> {
        return this.shifts
    }

    override suspend fun saveShift(shift: Shift) {
        shifts.add(shift)
    }
}