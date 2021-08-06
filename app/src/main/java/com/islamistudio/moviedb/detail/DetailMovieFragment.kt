package com.islamistudio.moviedb.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.islamistudio.moviedb.BuildConfig
import com.islamistudio.moviedb.MainActivity
import com.islamistudio.moviedb.R
import com.islamistudio.moviedb.core.data.Resource
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

            val movie = args.movie
            loadData(movie.id, false)

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData(id: Int, reload: Boolean) {
        viewModel.getMovie(id, reload).observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding?.progressBarView?.root?.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding?.progressBarView?.root?.visibility = View.GONE
                        showDetailTourism(it.data)
                    }
                    is Resource.Error -> {
                        binding?.progressBarView?.root?.visibility = View.GONE
                        binding?.viewError?.root?.visibility = View.VISIBLE
                    }
                }
            }
        })
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
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        binding?.fab?.isSelected = statusFavorite
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (activity != null) {
            (activity as MainActivity).hideNavBar(false)
            (activity as MainActivity).supportActionBar?.show()
        }
    }
}