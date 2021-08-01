package com.islamistudio.gamedb.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.islamistudio.gamedb.core.domain.model.Developer
import com.islamistudio.gamedb.core.domain.model.Platform
import com.islamistudio.gamedb.core.domain.model.Publisher

data class GameResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("metacritic")
    val metacritic: Int,

    @field:SerializedName("released")
    val released: String,

    @field:SerializedName("backgroundImage")
    val backgroundImage: String,

    @field:SerializedName("backgroundImageAdditional")
    val backgroundImageAdditional: String,

    @field:SerializedName("rating")
    val rating: Number,

    @field:SerializedName("platforms")
    val platforms: List<Platform>,

    @field:SerializedName("developers")
    val developers: List<Developer>,

    @field:SerializedName("publishers")
    val publishers: List<Publisher>,

)
