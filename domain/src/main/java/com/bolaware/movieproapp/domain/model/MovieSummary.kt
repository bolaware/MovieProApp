package com.bolaware.movieproapp.domain.model

import com.google.gson.annotations.SerializedName

data class MovieSummary (
    @SerializedName("Title")
    val title : String,
    @SerializedName("Year")
    val year : String,
    val imdbID : String,
    @SerializedName("Type")
    val type : String,
    @SerializedName("Poster")
    val posterUrl : String
)