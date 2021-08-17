package com.islamistudio.moviedb.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.islamistudio.moviedb.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getFavoriteMovie().asLiveData()

}