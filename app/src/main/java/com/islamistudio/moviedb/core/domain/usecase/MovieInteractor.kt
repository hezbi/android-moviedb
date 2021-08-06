package com.islamistudio.moviedb.core.domain.usecase

import com.islamistudio.moviedb.core.data.Resource
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {

    override fun getAllMovies(reload: Boolean) = movieRepository.getAllMovies(reload)

    override fun getMovie(id: Int, reload: Boolean) = movieRepository.getMovie(id, reload)

    override fun searchMovie(query: String) = movieRepository.searchMovie(query)

    override fun getFavoriteMovie() = movieRepository.getFavoriteMovie()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)

}