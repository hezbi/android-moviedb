
package com.islamistudio.gamedb.core.data

import com.islamistudio.gamedb.core.data.source.local.LocalDataSource
import com.islamistudio.gamedb.core.data.source.remote.RemoteDataSource
import com.islamistudio.gamedb.core.data.source.remote.network.ApiResponse
import com.islamistudio.gamedb.core.data.source.remote.response.GameResponse
import com.islamistudio.gamedb.core.domain.model.Game
import com.islamistudio.gamedb.core.domain.repository.IGameRepository
import com.islamistudio.gamedb.core.utils.AppExecutors
import com.islamistudio.gamedb.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGameRepository {

    override fun getAllGame(): Flow<Resource<List<Game>>> =
        object : NetworkBoundResource<List<Game>, List<GameResponse>>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getAllGame().map {
                    DataMapper.mapGameEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GameResponse>>> =
                remoteDataSource.getAllGame()

            override suspend fun saveCallResult(data: List<GameResponse>) {
                val gameList = DataMapper.mapGameResponsesToEntities(data)
                localDataSource.insertGame(gameList)
            }

        }.asFlow()

    override fun getFavoriteGame(): Flow<List<Game>> {
        return localDataSource.getFavoriteGame().map {
            DataMapper.mapGameEntitiesToDomain(it)
        }
    }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = DataMapper.mapGameDomainToEntity(game)
        appExecutors.diskIO().execute { localDataSource.setFavoriteGame(gameEntity, state) }
    }

}