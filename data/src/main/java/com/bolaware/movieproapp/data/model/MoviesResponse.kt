package com.bolaware.movieproapp.data.model

import com.bolaware.movieproapp.domain.model.MovieSummary
import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("Search")
    var movies : List<MovieSummary>,
    val totalResults : String,
    @SerializedName("Response")
    var response : Boolean

)