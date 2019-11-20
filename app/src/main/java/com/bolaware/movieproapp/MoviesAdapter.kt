package com.bolaware.movieproapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bolaware.movieproapp.domain.model.MovieSummary
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_list_item.view.*


class MoviesAdapter (val movies : MutableList<MovieSummary>, val clickListener: (MovieSummary, ImageView) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movies_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var movie : MovieSummary

        init {
            itemView.cardView.setOnClickListener {
                clickListener(movie, itemView.bannerIV)
            }
        }

        fun bind(movie: MovieSummary){
            this.movie = movie

            Picasso.get()
                .load(movie.posterUrl)
                .resize(60000, 2000)
                .centerInside()
                .placeholder(getRandomDrawbleColor())
                .into(itemView.bannerIV)

            ViewCompat.setTransitionName(itemView.bannerIV, movie.title)
        }
    }

    fun updateMovies(newPosts : List<MovieSummary>){
        val diffCallback = MovieDiffCallback(movies, newPosts)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        movies.clear()
        movies.addAll(newPosts)
        diffResult.dispatchUpdatesTo(this)
    }
}