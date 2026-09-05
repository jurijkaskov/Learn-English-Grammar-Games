package com.learnenglish.grammargames.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
import com.learnenglish.grammargames.core.database.entity.UserProgressEntity

@Database(
    entities = [UserProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GrammarGamesDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
}
