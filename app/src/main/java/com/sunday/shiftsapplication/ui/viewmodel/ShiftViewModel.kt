package com.sunday.shiftsapplication.ui.viewmodel

import androidx.lifecycle.*
import com.sunday.shiftsapplication.data.model.Shift
import com.sunday.shiftsapplication.data.repository.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.lang.Double.valueOf
import java.lang.Exception
import java.lang.Short.valueOf
import java.util.*

class ShiftViewModel constructor (private val repository: Repository): ViewModel(){

    private var _shifts = MutableLiveData<List<Shift>>()
    val shifts: LiveData<List<Shift>> = _shifts

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _loading

    private val _startDate = MutableStateFlow<Long>(0)
    private val _endDate = MutableStateFlow<Long>(0)
    private val _employee = MutableStateFlow("")
    private val _role = MutableStateFlow("")
    private val _color = MutableStateFlow("")

    fun setStartDate(date: Long) {
        _startDate.value = date
    }

    fun setEndDate(date: Long) {
        _endDate.value = date
    }

    fun setEmployee(employee: String) {
        _employee.value = employee
    }

    fun setRole(role: String) {
        _role.value = role
    }

    fun setColor(color: String) {
        _color.value = color
    }

    init {
        loadShifts()
    }

    fun createShift() = viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
        _success.value = false
    }) {
        val shift = Shift(
            name = _employee.value,
            startDate = Date(_startDate.value),
            endDate = Date(_endDate.value),
            role = _role.value,
            color = _color.value
        )
        repository.saveShift(shift)
        loadShifts()

        _success.value = true
        reset()
    }

    fun loadShifts() = viewModelScope.launch {
       try {
           _loading.value = true
           _shifts.value = repository.getShifts()
           _loading.value = false

       }catch (error: Exception){

       }
    }

    private fun reset(){
        _startDate.value = 0
        _endDate.value = 0
        _employee.value = ""
        _role.value = ""
        _color.value = ""
    }

    val isSubmitEnabled: Flow<Boolean> = combine(_startDate, _endDate, _employee, _role, _color) { startDate, endDate, employee, role, color ->
        return@combine (startDate.compareTo(0) != 0) and (endDate.compareTo(0) != 0) and employee.isNotEmpty() and role.isNotEmpty() and color.isNotEmpty()
    }
}