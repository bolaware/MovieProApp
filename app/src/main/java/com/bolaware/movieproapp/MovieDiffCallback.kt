package com.bolaware.movieproapp

import androidx.recyclerview.widget.DiffUtil
import com.bolaware.movieproapp.domain.model.MovieSummary

class MovieDiffCallback(val oldMovies : List<MovieSummary>, val newMovies : List<MovieSummary>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldMovies.size
    }

    override fun getNewListSize(): Int {
        return newMovies.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition].imdbID == newMovies[newItemPosition].imdbID
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMovies[oldItemPosition].title == newMovies[newItemPosition].title
    }
}