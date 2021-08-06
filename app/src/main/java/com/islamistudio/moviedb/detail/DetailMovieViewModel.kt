package com.islamistudio.moviedb.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.domain.usecase.MovieUseCase

class DetailMovieViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun getMovie(id: Int, reload: Boolean) = movieUseCase.getMovie(id, reload).asLiveData()

    fun setFavoriteMovie(movie: Movie, newStatus:Boolean) =
        movieUseCase.setFavoriteMovie(movie, newStatus)

}