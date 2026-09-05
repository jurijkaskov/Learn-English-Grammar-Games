package com.learnenglish.grammargames.feature.profile.character

data class CompanionPerk(
    val title: String,
    val description: String,
    val isUnlocked: Boolean
)

data class CharacterUiState(
    val name: String = "Ignis the Grammar Drake",
    val stageName: String = "Stage 1: Hatchling",
    val currentXp: Int = 180,
    val xpForNextEvolution: Int = 300,
    val stageEmoji: String = "🐉",
    val perks: List<CompanionPerk> = listOf(
        CompanionPerk("Streak Shield", "Protects 1 missed day of your learning streak", true),
        CompanionPerk("XP Fire Breath", "+10% bonus XP in all game sessions", true),
        CompanionPerk("Grammar Clairvoyance", "Removes 1 incorrect option once per game session", false)
    )
)
