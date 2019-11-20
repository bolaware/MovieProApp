package com.bolaware.movieproapp.data.source

import com.bolaware.movieproapp.data.repository.IRemote
import com.bolaware.movieproapp.data.repository.IDataSource
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val remote : IRemote) : IDataSource {
    override fun searchMovies(searchTerm: String): Single<List<MovieSummary>> {
        return remote.searchMovies(searchTerm)
    }

    override fun getMovie(imdbID: String): Single<Movie> {
        return remote.getMovie(imdbID)
    }
}