package com.bolaware.movieproapp.data

import com.bolaware.movieproapp.data.source.RemoteDataSource
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import com.bolaware.movieproapp.domain.repository.IMoviesProRepository
import io.reactivex.Single
import javax.inject.Inject

class MoviesProRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) : IMoviesProRepository{
    override fun searchMovies(searchTerm: String): Single<List<MovieSummary>> {
        return remoteDataSource.searchMovies(searchTerm)
    }

    override fun getMovie(imdbID: String): Single<Movie> {
        return remoteDataSource.getMovie(imdbID)
    }
}