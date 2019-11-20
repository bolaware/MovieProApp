package com.bolaware.movieproapp.di

import com.bolaware.movieproapp.data.MoviesProRepository
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {
    @Binds
    abstract fun bindDataRepository(dataRepository: MoviesProRepository): IMoviesProRepository
}