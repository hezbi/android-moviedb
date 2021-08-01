package com.islamistudio.gamedb.core.data.source.remote

import android.util.Log
import com.islamistudio.gamedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.gamedb.core.data.source.remote.network.ApiService
import com.islamistudio.gamedb.core.data.source.remote.response.GameResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllGame(): Flow<ApiResponse<List<GameResponse>>> {
        return flow {
            try {
                val response = apiService.getGameList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }
    }
}