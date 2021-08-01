package com.islamistudio.gamedb.core.data.source.local.room

import androidx.room.RoomDatabase

abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

}