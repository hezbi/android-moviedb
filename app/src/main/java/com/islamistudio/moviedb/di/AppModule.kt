package com.islamistudio.moviedb.di

import com.islamistudio.moviedb.core.domain.usecase.MovieInteractor
import com.islamistudio.moviedb.core.domain.usecase.MovieUseCase
import com.islamistudio.moviedb.detail.DetailMovieViewModel
import com.islamistudio.moviedb.favorite.FavoriteViewModel
import com.islamistudio.moviedb.home.HomeViewModel
import com.islamistudio.moviedb.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

@FlowPreview
@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}