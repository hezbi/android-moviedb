package com.islamistudio.moviedb.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamistudio.moviedb.core.R
import com.islamistudio.moviedb.core.domain.model.Movie

class MovieGridAdapter: RecyclerView.Adapter<MovieGridViewHolder>() {

    var list = listOf<Movie>()
    var delegate: RecyclerViewAdapterDelegate<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie_grid_item, parent, false)
        return MovieGridViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieGridViewHolder, position: Int) {
        holder.setData(
            list,
            position,
            delegate
        )
    }

    override fun getItemCount() = list.size
}