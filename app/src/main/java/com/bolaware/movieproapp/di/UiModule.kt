package com.bolaware.movieproapp.di


import com.bolaware.movieproapp.MainActivity
import com.bolaware.movieproapp.MoviesDetailsFragment
import com.bolaware.movieproapp.MoviesFragment
import com.bolaware.movieproapp.UiThread
import com.bolaware.movieproapp.domain.executor.PostExecutionThread
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributesMainFragment(): MoviesFragment


    @ContributesAndroidInjector
    abstract fun contributesMoviesDetailsFragment(): MoviesDetailsFragment


}