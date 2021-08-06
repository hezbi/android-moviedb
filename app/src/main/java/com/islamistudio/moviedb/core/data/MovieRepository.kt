package com.islamistudio.moviedb.core.data

import com.islamistudio.moviedb.core.data.source.local.LocalDataSource
import com.islamistudio.moviedb.core.data.source.remote.RemoteDataSource
import com.islamistudio.moviedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.moviedb.core.data.source.remote.response.MovieResponse
import com.islamistudio.moviedb.core.domain.model.Movie
import com.islamistudio.moviedb.core.domain.repository.IMovieRepository
import com.islamistudio.moviedb.core.utils.AppExecutors
import com.islamistudio.moviedb.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {

    override fun getAllMovies(reload: Boolean): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) =
                data == null || data.isEmpty()

            override fun shouldReload() = reload

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

        }.asFlow()

    override fun getMovie(id: Int, reload: Boolean): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, MovieResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovie(id).map {
                    DataMapper.mapMovieEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?) =
                data == null

            override fun shouldReload() = reload

            override suspend fun createCall(): Flow<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovie(id)

            override suspend fun saveCallResult(data: MovieResponse) {
            }

        }.asFlow()

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.searchMovie(query).map {
                    DataMapper.mapMovieEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?) =
                data == null || data.isEmpty()

            override fun shouldReload() = false

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.searchMovie(query)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovie(movieList)
            }

        }.asFlow()

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