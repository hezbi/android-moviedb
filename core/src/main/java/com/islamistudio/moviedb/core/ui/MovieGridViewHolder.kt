package com.islamistudio.moviedb.core.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.islamistudio.moviedb.core.BuildConfig
import com.islamistudio.moviedb.core.databinding.ItemAdapterMovieGridBinding
import com.islamistudio.moviedb.core.domain.model.Movie

class MovieGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemAdapterMovieGridBinding.bind(itemView)

    fun setData(
        data: List<Movie>,
        position: Int,
        delegate: RecyclerViewAdapterDelegate<Movie>?
    ) {
        with(binding) {
            Glide.with(itemView.context)
                .load(BuildConfig.IMAGE_URL + data[position].posterPath)
                .into(ivMovieBanner)
            tvMovieTitle.text = data[position].originalTitle

            root.setOnClickListener {
                delegate?.onClick(data[position])
            }
        }
    }

}