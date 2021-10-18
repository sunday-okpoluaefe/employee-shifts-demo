package com.sunday.shiftsapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sunday.shiftsapplication.data.repository.Repository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ShiftViewModelFactory @Inject constructor(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if(modelClass.isAssignableFrom(ShiftViewModel::class.java)) return ShiftViewModel(repository) as T
        throw IllegalArgumentException("Invalid view model class")
    }
}