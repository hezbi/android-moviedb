package com.islamistudio.gamedb.core.utils

import com.islamistudio.gamedb.core.data.source.local.entity.DeveloperEntity
import com.islamistudio.gamedb.core.data.source.local.entity.GameEntity
import com.islamistudio.gamedb.core.data.source.local.entity.PlatformEntity
import com.islamistudio.gamedb.core.data.source.local.entity.PublisherEntity
import com.islamistudio.gamedb.core.data.source.remote.response.GameResponse
import com.islamistudio.gamedb.core.domain.model.Developer
import com.islamistudio.gamedb.core.domain.model.Game
import com.islamistudio.gamedb.core.domain.model.Platform
import com.islamistudio.gamedb.core.domain.model.Publisher

object DataMapper {

    fun mapGameResponsesToEntities(input: List<GameResponse>): List<GameEntity> =
        input.map {
            GameEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                metacritic = it.metacritic,
                released = it.released,
                backgroundImage = it.backgroundImage,
                backgroundImageAdditional = it.backgroundImageAdditional,
                rating = it.rating,
                platforms = mapPlatformsDomainToEntities(it.platforms),
                developers = mapDevelopersDomainToEntities(it.developers),
                publishers = mapPublishersDomainToEntities(it.publishers),
                isFavorite = false
            )
        }

    private fun mapPlatformsDomainToEntities(input: List<Platform>): List<PlatformEntity> {
        val platformList = ArrayList<PlatformEntity>()
        input.map {
            val platform = PlatformEntity(
                id = it.id,
                slug = it.slug,
                name = it.name,
                imageBackground = it.imageBackground
            )
            platformList.add(platform)
        }
        return platformList
    }

    private fun mapDevelopersDomainToEntities(input: List<Developer>): List<DeveloperEntity> {
        val developerList = ArrayList<DeveloperEntity>()
        input.map {
            val developer = DeveloperEntity(
                id = it.id,
                name = it.name
            )
            developerList.add(developer)
        }
        return developerList
    }

    private fun mapPublishersDomainToEntities(input: List<Publisher>): List<PublisherEntity> {
        val publisherList = ArrayList<PublisherEntity>()
        input.map {
            val publisher = PublisherEntity(
                id = it.id,
                name = it.name
            )
            publisherList.add(publisher)
        }
        return publisherList
    }

    fun mapGameEntitiesToDomain(input: List<GameEntity>): List<Game> =
        input.map {
            Game(
                id = it.id,
                name = it.name,
                description = it.description,
                metacritic = it.metacritic,
                released = it.released,
                backgroundImage = it.backgroundImage,
                backgroundImageAdditional = it.backgroundImageAdditional,
                rating = it.rating,
                platforms = mapPlatformEntitiesToDomain(it.platforms),
                developers = mapDeveloperEntitiesToDomain(it.developers),
                publishers = mapPublisherEntitiesToDomain(it.publishers),
                isFavorite = it.isFavorite
            )
        }

    private fun mapPlatformEntitiesToDomain(input: List<PlatformEntity>): List<Platform> =
        input.map {
            Platform(
                id = it.id,
                slug = it.slug,
                name = it.name,
                imageBackground = it.imageBackground
            )
        }

    private fun mapDeveloperEntitiesToDomain(input: List<DeveloperEntity>): List<Developer> =
        input.map {
            Developer(
                id = it.id,
                name = it.name
            )
        }

    private fun mapPublisherEntitiesToDomain(input: List<PublisherEntity>): List<Publisher> =
        input.map {
            Publisher(
                id = it.id,
                name = it.name
            )
        }

    fun mapGameDomainToEntity(input: Game) = GameEntity(
        id = input.id,
        name = input.name,
        description = input.description,
        metacritic = input.metacritic,
        released = input.released,
        backgroundImage = input.backgroundImage,
        backgroundImageAdditional = input.backgroundImageAdditional,
        rating = input.rating,
        platforms = mapPlatformsDomainToEntities(input.platforms),
        developers = mapDevelopersDomainToEntities(input.developers),
        publishers = mapPublishersDomainToEntities(input.publishers),
        isFavorite = input.isFavorite
    )

}