package com.learnenglish.grammargames.core.designsystem.component.asset

/**
 * GraphicAssetCategory: Functional asset families defined in GRAPHIC_ASSETS.md.
 */
enum class GraphicAssetCategory {
    CHARACTER,
    BACKGROUND,
    ENVIRONMENT,
    WORLD,
    PROPS,
    REWARD,
    ACHIEVEMENT,
    BADGE,
    GAME,
    ILLUSTRATION,
    ICON,
    EFFECT,
    DECORATION
}

/**
 * ChestState: Discrete canonical visual states for reward treasure chests.
 */
enum class ChestState {
    CLOSED,
    READY,
    OPEN,
    CLAIMED
}

/**
 * Canonical game types featured in the Games Arena hub.
 */
enum class GameAssetType {
    CROSSWORD,
    WORD_SEARCH,
    MEMORY,
    GRAMMAR_BATTLE,
    SPEED_CHALLENGE,
    SENTENCE_RACE
}

/**
 * Canonical reward asset tokens.
 */
enum class RewardAssetType {
    CHEST_CLOSED,
    CHEST_READY,
    CHEST_OPEN,
    CHEST_CLAIMED,
    XP_STAR,
    COIN_GOLD,
    KEY_SILVER,
    KEY_GOLD
}

/**
 * Canonical environment landscape props.
 */
enum class EnvironmentAssetType {
    CLOUD_SMALL,
    CLOUD_MEDIUM,
    CLOUD_WIDE,
    TREE_ROUND,
    TREE_PINE,
    BUSH_ROUND,
    CASTLE_LANDMARK,
    STONE_PATH_SEGMENT
}

/**
 * Lifecycle status of an asset within GRAPHIC_ASSETS.md.
 */
enum class AssetStatus {
    PLANNED,
    GENERATED,
    REVIEWED,
    APPROVED,
    INTEGRATED,
    DEPRECATED
}

/**
 * Metadata record for tracking graphic assets in the manifest registry.
 */
data class GraphicAssetMetadata(
    val id: String,
    val category: GraphicAssetCategory,
    val description: String,
    val status: AssetStatus = AssetStatus.APPROVED,
    val isTransparent: Boolean = true,
    val isGlobalReusable: Boolean = true,
    val displaySizeRange: String
)
