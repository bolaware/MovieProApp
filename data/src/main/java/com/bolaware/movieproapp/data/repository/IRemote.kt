package com.bolaware.movieproapp.data.repository


import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import io.reactivex.Single

interface IRemote {
    fun searchMovies(searchTerm : String) : Single<List<MovieSummary>>

    fun getMovie(imdbID : String) : Single<Movie>
}