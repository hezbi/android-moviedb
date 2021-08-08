package com.islamistudio.moviedb.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.islamistudio.moviedb.BuildConfig
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.databinding.FragmentDetailMovieBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailMovieFragment : Fragment() {

    private val args by navArgs<DetailMovieFragmentArgs>()
    private val viewModel: DetailMovieViewModel by viewModel()

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            (activity as MainActivity).hideNavBar(true)
            (activity as MainActivity).supportActionBar?.hide()

            (activity as MainActivity).apply {
                setSupportActionBar(binding?.toolbar)
                if (supportActionBar != null) {
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    binding?.toolbar?.setNavigationOnClickListener { onBackPressed() }
                }

            }


            val movie = args.movie
            showDetailTourism(movie)

        }
    }

    private fun showDetailTourism(detailMovie: Movie?) {
        detailMovie?.let {
            binding?.content?.tvDetailDescription?.text = detailMovie.overview
            Glide.with(this@DetailMovieFragment)
                .load(BuildConfig.IMAGE_URL + detailMovie.backdropPath)
                .into(binding?.ivDetailImage!!)

            var statusFavorite = detailMovie.isFavorite
            setStatusFavorite(statusFavorite)
            binding?.fab?.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteMovie(detailMovie, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
            binding?.toolbar?.title = detailMovie.originalTitle
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding?.fab?.isSelected = statusFavorite
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}