package com.islamistudio.gamedb.core.data.source.local.room

import androidx.room.*
import com.islamistudio.gamedb.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    @Query("SELECT * FROM game")
    fun getAllGame(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game where isFavorite = 1")
    fun getFavoriteGame(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(gameList: List<GameEntity>)

    @Update
    fun updateGameFavorite(game: GameEntity)

}