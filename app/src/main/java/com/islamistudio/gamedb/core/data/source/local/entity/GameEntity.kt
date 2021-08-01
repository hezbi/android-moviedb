package com.islamistudio.gamedb.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.islamistudio.gamedb.core.domain.model.Developer
import com.islamistudio.gamedb.core.domain.model.Platform
import com.islamistudio.gamedb.core.domain.model.Publisher

@Entity(tableName = "game")
data class GameEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "metacritic")
    val metacritic: Int,

    @ColumnInfo(name = "released")
    val released: String,

    @ColumnInfo(name = "backgroundImage")
    val backgroundImage: String,

    @ColumnInfo(name = "backgroundImageAdditional")
    val backgroundImageAdditional: String,

    @ColumnInfo(name = "rating")
    val rating: Number,

    @ColumnInfo(name = "platforms")
    val platforms: List<PlatformEntity>,

    @ColumnInfo(name = "developers")
    val developers: List<DeveloperEntity>,

    @ColumnInfo(name = "publishers")
    val publishers: List<PublisherEntity>,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

)
