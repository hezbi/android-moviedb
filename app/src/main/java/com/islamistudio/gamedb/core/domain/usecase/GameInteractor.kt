package com.islamistudio.gamedb.core.domain.usecase

import com.islamistudio.gamedb.core.domain.model.Game
import com.islamistudio.gamedb.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository): GameUseCase {

    override fun getAllGame() = gameRepository.getAllGame()

    override fun getFavoriteGame() = gameRepository.getFavoriteGame()

    override fun setFavoriteGame(game: Game, state: Boolean) = gameRepository.setFavoriteGame(game, state)

}