package com.learnenglish.grammargames.core.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `skill_mastery` (
                `skillId` TEXT NOT NULL,
                `topicId` TEXT NOT NULL,
                `score` INTEGER NOT NULL,
                `rawAccuracy` REAL NOT NULL,
                `totalAttempts` INTEGER NOT NULL,
                `successfulAttempts` INTEGER NOT NULL,
                `confidence` REAL NOT NULL,
                `lastPracticedTimestamp` INTEGER,
                `status` TEXT NOT NULL,
                `decayFactor` REAL NOT NULL,
                `algorithmVersion` INTEGER NOT NULL,
                PRIMARY KEY(`skillId`)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS `index_skill_mastery_topicId` ON `skill_mastery` (`topicId`)
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `topic_mastery` (
                `topicId` TEXT NOT NULL,
                `score` INTEGER NOT NULL,
                `status` TEXT NOT NULL,
                `skillsCount` INTEGER NOT NULL,
                `masteredSkillsCount` INTEGER NOT NULL,
                `lastPracticedTimestamp` INTEGER,
                PRIMARY KEY(`topicId`)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `question_attempts` (
                `id` TEXT NOT NULL,
                `questionId` TEXT NOT NULL,
                `topicId` TEXT NOT NULL,
                `skillId` TEXT,
                `isCorrect` INTEGER NOT NULL,
                `difficulty` TEXT NOT NULL,
                `hintsUsed` INTEGER NOT NULL,
                `timeSpentMs` INTEGER NOT NULL,
                `timestamp` INTEGER NOT NULL,
                PRIMARY KEY(`id`)
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS `index_question_attempts_topicId` ON `question_attempts` (`topicId`)
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS `index_question_attempts_skillId` ON `question_attempts` (`skillId`)
            """.trimIndent()
        )
    }
}
