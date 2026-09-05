package com.learnenglish.grammargames.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey {

    // Bootstrap
    @Serializable
    data object Bootstrap : AppNavKey

    // Onboarding Flow
    @Serializable
    data object Welcome : AppNavKey

    @Serializable
    data object GoalSelection : AppNavKey

    @Serializable
    data object LevelSelection : AppNavKey

    @Serializable
    data object PlacementTest : AppNavKey

    @Serializable
    data object BookSelection : AppNavKey

    @Serializable
    data object DailyGoal : AppNavKey

    @Serializable
    data object OnboardingComplete : AppNavKey

    // Main Root Tabs (Accessible via Bottom Navigation)
    @Serializable
    data object Home : AppNavKey

    @Serializable
    data object Learn : AppNavKey

    @Serializable
    data object Games : AppNavKey

    @Serializable
    data object Review : AppNavKey

    @Serializable
    data object Profile : AppNavKey

    // Nested / Detail Destinations (Minimal primitive arguments only)
    @Serializable
    data class Topic(val topicId: String) : AppNavKey

    @Serializable
    data class Lesson(val topicId: String, val lessonId: String) : AppNavKey

    @Serializable
    data class Test(val topicId: String) : AppNavKey

    @Serializable
    data class GameSession(
        val gameType: String,
        val topicId: String? = null
    ) : AppNavKey

    @Serializable
    data class Results(
        val sessionId: String,
        val resultType: String = "LESSON"
    ) : AppNavKey

    @Serializable
    data object Achievements : AppNavKey

    @Serializable
    data class AchievementDetails(val achievementId: String) : AppNavKey

    @Serializable
    data object Character : AppNavKey

    @Serializable
    data object CharacterCustomization : AppNavKey

    @Serializable
    data object Settings : AppNavKey

    @Serializable
    data object Mistakes : AppNavKey

    @Serializable
    data class ReviewSession(val reviewType: String = "MISTAKES") : AppNavKey

    // Debug / Visual QA
    @Serializable
    data object DesignSystemShowcase : AppNavKey

    @Serializable
    data object CurriculumInspector : AppNavKey
}
