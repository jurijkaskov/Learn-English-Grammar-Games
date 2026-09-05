package com.learnenglish.grammargames.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.learnenglish.grammargames.core.database.dao.MasteryDao
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
import com.learnenglish.grammargames.core.database.entity.QuestionAttemptEntity
import com.learnenglish.grammargames.core.database.entity.SkillMasteryEntity
import com.learnenglish.grammargames.core.database.entity.TopicMasteryEntity
import com.learnenglish.grammargames.core.database.entity.UserProgressEntity

@Database(
    entities = [
        UserProgressEntity::class,
        SkillMasteryEntity::class,
        TopicMasteryEntity::class,
        QuestionAttemptEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class GrammarGamesDatabase : RoomDatabase() {
    abstract fun userProgressDao(): UserProgressDao
    abstract fun masteryDao(): MasteryDao
}
