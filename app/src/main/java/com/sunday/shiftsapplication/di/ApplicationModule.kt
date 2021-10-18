package com.sunday.shiftsapplication.di

import co.infinum.retromock.Retromock
import com.sunday.shiftsapplication.data.repository.Repository
import com.sunday.shiftsapplication.data.repository.ShiftRepository
import com.sunday.shiftsapplication.service.ShiftService
import com.sunday.shiftsapplication.ui.viewmodel.ShiftViewModelFactory
import com.sunday.shiftsapplication.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    companion object {

        @Singleton
        @Provides
        fun provideRetrofit() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideMockRetrofit(retrofit: Retrofit) : Retromock =
            Retromock.Builder().retrofit(retrofit).build()

        @Singleton
        @Provides
        fun provideShiftService(retrofit: Retromock) : ShiftService = retrofit
            .create(ShiftService::class.java)

        @Singleton
        @Provides
        fun provideShiftRepository(service: ShiftService) : Repository = ShiftRepository(service)

        @Singleton
        @Provides
        fun provideShiftViewModelFactory(repository: Repository) = ShiftViewModelFactory(repository)
    }

}