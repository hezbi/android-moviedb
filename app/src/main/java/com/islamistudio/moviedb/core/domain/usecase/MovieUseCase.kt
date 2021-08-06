package com.islamistudio.moviedb.core.domain.usecase

import com.islamistudio.moviedb.core.data.Resource
import com.islamistudio.moviedb.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(reload: Boolean): Flow<Resource<List<Movie>>>
    fun getMovie(id: Int, reload: Boolean): Flow<Resource<Movie>>
    fun searchMovie(query: String): Flow<Resource<List<Movie>>>
    fun getFavoriteMovie(): Flow<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}