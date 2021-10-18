package com.sunday.shiftsapplication.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sunday.shiftsapplication.data.factory.MockRepository
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModel
import com.sunday.shiftsapplication.utils.MainCoroutineRule
import com.sunday.shiftsapplication.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ShiftViewModelTest {
    private lateinit var viewModel: ShiftViewModel

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        viewModel = ShiftViewModel(MockRepository())
    }

    @Test
    fun test_LoadShifts_is_Successful(){
        this.viewModel.loadShifts()
        val shifts = this.viewModel.shifts.getOrAwaitValue()
        assertThat(shifts.size).isEqualTo(1)
    }

    @Test
    fun test_createShift_is_Successful(){
        val date = Calendar.getInstance().timeInMillis
        this.viewModel.setEmployee("Anna")
        this.viewModel.setEndDate(date)
        this.viewModel.setStartDate(date)
        this.viewModel.setColor("red")
        this.viewModel.setRole("Prep")

        this.viewModel.createShift()
        val shifts = this.viewModel.shifts.getOrAwaitValue()
        assertThat(shifts.size).isEqualTo(2)
    }


}