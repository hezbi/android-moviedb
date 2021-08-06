package com.islamistudio.moviedb.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.domain.model.Movie

class MovieAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    var list = listOf<Movie>()
    var delegate: RecyclerViewAdapterDelegate<Movie>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setData(
            list,
            position,
            delegate
        )
    }

    override fun getItemCount() = list.size
}