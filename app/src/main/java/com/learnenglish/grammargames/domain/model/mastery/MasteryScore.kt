package com.learnenglish.grammargames.domain.model.mastery

data class MasteryScore(
    val score: Int = 0, // 0..100
    val rawAccuracy: Float = 0.0f, // 0.0..1.0
    val confidence: Float = 0.0f, // 0.0..1.0
    val totalAttempts: Int = 0,
    val successfulAttempts: Int = 0,
    val decayFactor: Float = 1.0f,
    val status: MasteryStatus = MasteryStatus.NOT_STARTED,
    val lastPracticedTimestamp: Long? = null
)
