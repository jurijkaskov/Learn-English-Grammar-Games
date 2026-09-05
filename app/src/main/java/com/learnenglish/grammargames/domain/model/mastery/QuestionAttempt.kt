package com.learnenglish.grammargames.domain.model.mastery

import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel

data class QuestionAttempt(
    val id: String,
    val questionId: String,
    val topicId: String,
    val skillId: String? = null,
    val isCorrect: Boolean,
    val difficulty: DifficultyLevel = DifficultyLevel.NORMAL,
    val hintsUsed: Int = 0,
    val timeSpentMs: Long = 0L,
    val timestamp: Long = System.currentTimeMillis()
)
