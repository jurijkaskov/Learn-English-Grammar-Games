package com.learnenglish.grammargames.feature.profile.achievements

data class AchievementItem(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val isUnlocked: Boolean,
    val progress: Float
)

data class AchievementsUiState(
    val totalBadgesEarned: Int = 3,
    val totalBadgesCount: Int = 8,
    val achievements: List<AchievementItem> = listOf(
        AchievementItem("first_lesson", "First Step", "Complete your first grammar lesson", "🌱", true, 1.0f),
        AchievementItem("streak_3", "Habit Builder", "Maintain a 3-day study streak", "🔥", true, 1.0f),
        AchievementItem("speed_master", "Speed Demon", "Score 150+ in Speed Tenses Arena", "⚡", true, 1.0f),
        AchievementItem("perfect_score", "Flawless Grammar", "Achieve 100% accuracy on a Topic Test", "🎯", false, 0.7f),
        AchievementItem("dragon_evo", "Dragon Tamer", "Evolve your companion to Stage 2", "🐉", false, 0.45f),
        AchievementItem("error_slayer", "Zero Tolerance", "Clear all items in your Mistakes Notebook", "🛡️", false, 0.6f)
    )
)
