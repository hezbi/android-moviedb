package com.islamistudio.gamedb.core.data.source.remote.network

import com.islamistudio.gamedb.core.data.source.remote.response.GameListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("/games")
    suspend fun getGameList(): GameListResponse

}