package com.islamistudio.moviedb.core.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islamistudio.moviedb.core.BuildConfig
import com.islamistudio.moviedb.core.databinding.ItemAdapterMovieListBinding
import com.islamistudio.moviedb.core.domain.model.Movie

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemAdapterMovieListBinding.bind(itemView)

    fun setData(
        data: List<Movie>,
        position: Int,
        delegate: RecyclerViewAdapterDelegate<Movie>?
    ) {
        with(binding) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + data[position].backdropPath)
                .into(ivMovieBanner)
            tvMovieTitle.text = data[position].originalTitle
            tvRating.text = data[position].voteAverage.toString()

            root.setOnClickListener {
                delegate?.onClick(data[position])
            }
        }
    }

}