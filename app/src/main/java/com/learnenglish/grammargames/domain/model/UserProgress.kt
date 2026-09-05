package com.learnenglish.grammargames.domain.model

data class UserProgress(
    val totalXp: Long = 0L,
    val level: Int = 1,
    val streakDays: Int = 0
)
