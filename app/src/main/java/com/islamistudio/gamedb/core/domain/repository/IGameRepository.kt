package com.islamistudio.gamedb.core.domain.repository

import com.islamistudio.gamedb.core.data.Resource
import com.islamistudio.gamedb.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGame(): Flow<Resource<List<Game>>>
    fun getFavoriteGame(): Flow<List<Game>>
    fun setFavoriteGame(game: Game, state: Boolean)
}