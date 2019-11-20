package com.bolaware.movieproapp.remote

import com.bolaware.movieproapp.data.model.MoviesResponse
import com.bolaware.movieproapp.domain.model.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieProService {
    @GET("/")
    fun searchMovies(@Query("s") searchTerm : String,
                     @Query("apikey") apiKey : String = OMDB_API_KEY,
                     @Query("type") type : String = MOVIE_TYPE) : Single<MoviesResponse>


    @GET("/")
    fun fecthMovie(@Query("i") imdbID : String,
                     @Query("apikey") apiKey : String = OMDB_API_KEY) : Single<Movie>
}