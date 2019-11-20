package com.bolaware.movieproapp.di

import androidx.lifecycle.ViewModelProvider
import com.bolaware.movieproapp.MainActivity
import androidx.lifecycle.ViewModelProviders
import com.bolaware.movieproapp.presentation.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    ViewModelModule::class
])
abstract class PresentationModule {

    @ContributesAndroidInjector(
        modules = [
            InjectViewModelModule::class
        ]
    )
    abstract fun bind(): MainActivity



    @Module
    class InjectViewModelModule {
        @Provides
        fun provideMainViewModel(
            factory: ViewModelProvider.Factory,
            target: MainActivity
        ) = ViewModelProviders.of(target, factory).get(MainViewModel::class.java)
    }
}