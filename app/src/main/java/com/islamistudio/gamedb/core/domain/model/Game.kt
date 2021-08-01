package com.islamistudio.gamedb.core.domain.model

data class Game(
    val id: Int,
    val name: String,
    val description: String,
    val metacritic: Int,
    val released: String,
    val backgroundImage: String,
    val backgroundImageAdditional: String,
    val rating: Number,
    val platforms: List<Platform>,
    val developers: List<Developer>,
    val publishers: List<Publisher>,
    val isFavorite: Boolean
)
