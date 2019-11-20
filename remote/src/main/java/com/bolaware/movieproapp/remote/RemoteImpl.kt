package com.bolaware.movieproapp.remote

import com.bolaware.movieproapp.data.repository.IRemote
import com.bolaware.movieproapp.domain.model.Movie
import com.bolaware.movieproapp.domain.model.MovieSummary
import io.reactivex.Single
import javax.inject.Inject

const val OMDB_API_KEY = "379b7593"
const val MOVIE_TYPE = "movie"

class RemoteImpl @Inject constructor(private val service: MovieProService) :
    IRemote {
    override fun searchMovies(searchTerm : String) : Single<List<MovieSummary>> {
        return service.searchMovies(searchTerm).map {
            if(it.response) it.movies else emptyList()
        }
    }


    override fun getMovie(imdbID: String): Single<Movie> {
        return service.fecthMovie(imdbID)
    }
}