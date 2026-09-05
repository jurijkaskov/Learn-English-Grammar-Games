package com.learnenglish.grammargames.domain.model.mastery

data class MasteryExplanation(
    val baseAccuracy: Float,
    val confidenceFactor: Float,
    val averageDifficultyWeight: Float,
    val hintPenaltyFactor: Float,
    val decayFactor: Float,
    val finalScore: Int,
    val summaryText: String
)
