package com.islamistudio.moviedb.core.domain.usecase

import com.islamistudio.moviedb.core.data.Resource
import com.islamistudio.moviedb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<com.islamistudio.moviedb.core.data.Resource<List<Movie>>>
    fun getMovie(id: Int): Flow<com.islamistudio.moviedb.core.data.Resource<Movie>>
    fun searchMovie(query: String): Flow<List<Movie>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}