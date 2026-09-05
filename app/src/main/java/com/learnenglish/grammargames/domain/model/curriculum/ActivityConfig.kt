package com.learnenglish.grammargames.domain.model.curriculum

sealed interface ActivityConfig

data class PracticeConfig(
    val shuffleQuestions: Boolean = true,
    val allowRetry: Boolean = true,
    val showInstantExplanation: Boolean = true
) : ActivityConfig

data class TestConfig(
    val questionCount: Int = 10,
    val passThreshold: Float = 0.8f,
    val timeLimitSeconds: Int? = null,
    val randomizeQuestions: Boolean = true
) : ActivityConfig

data class SpeedChallengeConfig(
    val timeLimitSeconds: Int = 60,
    val questionCount: Int = 20,
    val comboEnabled: Boolean = true,
    val bonusTimePerCorrectSec: Int = 2
) : ActivityConfig

data class CrosswordConfig(
    val gridSize: Int = 9,
    val wordCount: Int = 6,
    val allowClueReveal: Boolean = true
) : ActivityConfig

data class WordSearchConfig(
    val gridSize: Int = 10,
    val wordCount: Int = 8,
    val allowDiagonal: Boolean = false
) : ActivityConfig
