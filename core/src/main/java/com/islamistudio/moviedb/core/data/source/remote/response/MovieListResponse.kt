package com.islamistudio.moviedb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse(

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("total_results")
    val totalResults: Int,

    @field:SerializedName("results")
    val results: List<MovieResponse>

)
