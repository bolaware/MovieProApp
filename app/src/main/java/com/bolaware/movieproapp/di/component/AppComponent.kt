package com.bolaware.movieproapp.di.component

import android.app.Application
import com.bolaware.movieproapp.MyApp
import com.bolaware.movieproapp.di.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class, RemoteModule::class, ViewModelModule::class,
            ApplicationModule::class, DataModule::class, UiModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: MyApp)
}