package com.learnenglish.grammargames.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question_attempts",
    indices = [
        Index(value = ["topicId"]),
        Index(value = ["skillId"])
    ]
)
data class QuestionAttemptEntity(
    @PrimaryKey
    val id: String,
    val questionId: String,
    val topicId: String,
    val skillId: String?,
    val isCorrect: Boolean,
    val difficulty: String,
    val hintsUsed: Int,
    val timeSpentMs: Long,
    val timestamp: Long
)
