package com.islamistudio.moviedb.core.data.source.remote

import android.util.Log
import com.islamistudio.moviedb.BuildConfig
import com.islamistudio.moviedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.moviedb.core.data.source.remote.network.ApiService
import com.islamistudio.moviedb.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovieList(BuildConfig.API_KEY)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource.getAllMovies", e.toString())
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
                Log.e("RemoteDataSource.getMovie", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovie(query: String): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.searchMovie(BuildConfig.API_KEY, query)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource.searchMovie", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}