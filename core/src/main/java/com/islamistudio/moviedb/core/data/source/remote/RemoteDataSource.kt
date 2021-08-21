package com.islamistudio.moviedb.core.data.source.remote

import com.islamistudio.moviedb.core.BuildConfig
import com.islamistudio.moviedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.moviedb.core.data.source.remote.network.ApiService
import com.islamistudio.moviedb.core.data.source.remote.response.MovieListResponse
import com.islamistudio.moviedb.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<MovieListResponse>> {
        return flow {
            try {
                val response = apiService.getMovieList(BuildConfig.API_KEY)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovie(id: Int): Flow<ApiResponse<MovieResponse>> {
        return flow {
            try {
                val response = apiService.getMovie(id, BuildConfig.API_KEY)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}