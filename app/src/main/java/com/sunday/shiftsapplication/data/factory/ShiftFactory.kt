package com.sunday.shiftsapplication.data.factory

import com.sunday.shiftsapplication.data.model.Shift
import java.util.*

class ShiftFactory {
    companion object{
        fun shift(): Shift = Shift(
            role = "Waiter",
            name = "Anna",
            color = "red",
            startDate = Date(),
            endDate = Date(), )

        fun shiftOther()= Shift(
            role = "Prep",
            name = "Anton",
            color = "blue",
            startDate = Date(),
            endDate = Date(), )

        fun shifts(): List<Shift> = listOf(shift())
    }
}