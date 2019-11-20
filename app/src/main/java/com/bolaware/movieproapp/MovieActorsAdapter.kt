package com.bolaware.movieproapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movies_actor_item.view.*

class MovieActorsAdapter(val actorNames : List<String>) : RecyclerView.Adapter<MovieActorsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieActorsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movies_actor_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieActorsAdapter.ViewHolder, position: Int) {
        holder.bind(actorNames[position])
    }

    override fun getItemCount(): Int = actorNames.size


    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(actorName :  String){
            itemView.actorDisplayIV.searchForActorAndLoad(actorName)
            itemView.nameTV.text = actorName
        }
    }
}