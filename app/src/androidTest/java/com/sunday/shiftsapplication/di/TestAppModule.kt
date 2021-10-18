package com.sunday.shiftsapplication.di

import com.sunday.shiftsapplication.data.factory.MockRepository
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    fun provideShiftViewModelFactory() = ShiftViewModelFactory(MockRepository())
}