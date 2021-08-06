package com.islamistudio.moviedb.core.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DataConverter {

    @TypeConverter
    fun <T> fromList(list: List<T>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun <T> toList(json: String): List<T> {
        return Gson().fromJson(json, object : TypeToken<T>() {}.type)
    }
}