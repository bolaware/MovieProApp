package com.bolaware.movieproapp.di

import com.bolaware.movieproapp.BuildConfig
import com.bolaware.movieproapp.data.repository.IRemote
import com.bolaware.movieproapp.remote.MovieProRetrofitFactory
import com.bolaware.movieproapp.remote.MovieProService
import com.bolaware.movieproapp.remote.RemoteImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesApiService(): MovieProService {
            return MovieProRetrofitFactory.makeBarterServiceFactory(BuildConfig.DEBUG)
        }
    }


    @Binds
    abstract fun bindRemote(remoteImpl: RemoteImpl)  : IRemote
}