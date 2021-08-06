package com.islamistudio.moviedb.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val isFavorite: Boolean
): Parcelable
