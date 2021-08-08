package com.islamistudio.moviedb.core.data

import com.islamistudio.moviedb.core.data.source.local.LocalDataSource
import com.islamistudio.moviedb.core.data.source.remote.RemoteDataSource
import com.islamistudio.moviedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.moviedb.core.data.source.remote.response.MovieListResponse
import com.islamistudio.moviedb.core.data.source.remote.response.MovieResponse
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.domain.repository.IMovieRepository
import com.islamistudio.moviedb.core.utils.AppExecutors
import com.islamistudio.moviedb.core.utils.DataMapper
import kotlinx.coroutines.flow.*

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, MovieListResponse>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) = true

            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: MovieListResponse) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data.results)
                localDataSource.insertMovies(movieList)
            }

        }.asFlow()

    override fun getMovie(id: Int): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovie(id).map {
                    DataMapper.mapMovieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?) = true

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override suspend fun saveCallResult(data: MovieResponse) {
            }

        }.asFlow()

    override suspend fun searchMovie(query: String): List<Movie> {
        val response = remoteDataSource.searchMovie(query).results
        val movieList = DataMapper.mapMovieResponsesToEntities(response)
        localDataSource.insertMovies(movieList)
        return DataMapper.mapMovieResponsesToDomain(response)
    }

    override fun getFavoriteMovie(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovie().map {
            DataMapper.mapMovieEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }

}