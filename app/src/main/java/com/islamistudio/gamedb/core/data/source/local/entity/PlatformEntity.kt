package com.islamistudio.gamedb.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patform")
data class PlatformEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "slug")
    val slug: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "imageBackground")
    val imageBackground: String

)
