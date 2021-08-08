package com.islamistudio.moviedb.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "movie")
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "originalTitle")
    val originalTitle: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "backdropPath")
    val backdropPath: String,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,

    @ColumnInfo(name = "releaseDate")
    val releaseDate: String,

    @ColumnInfo(name = "voteAverage")
    val voteAverage: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false

)
