package com.islamistudio.moviedb.di

import com.islamistudio.moviedb.core.domain.usecase.MovieInteractor
import com.islamistudio.moviedb.core.domain.usecase.MovieUseCase
import com.islamistudio.moviedb.detail.DetailMovieViewModel
import com.islamistudio.moviedb.favorite.FavoriteViewModel
import com.islamistudio.moviedb.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}