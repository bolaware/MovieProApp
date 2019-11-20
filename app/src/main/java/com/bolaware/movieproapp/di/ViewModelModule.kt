package com.bolaware.movieproapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bolaware.movieproapp.presentation.MainViewModel
import com.bolaware.movieproapp.di.helpers.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(
        viewModel: MainViewModel
    ): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}


@MapKey
@Target(AnnotationTarget.FUNCTION)
annotation class ViewModelKey(
        val value: KClass<out ViewModel>
)