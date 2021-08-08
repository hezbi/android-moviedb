package com.islamistudio.moviedb.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.islamistudio.moviedb.core.domain.usecase.MovieUseCase

class HomeViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    fun movie() = movieUseCase.getAllMovies().asLiveData()

}