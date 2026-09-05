package com.learnenglish.grammargames.core.designsystem.component.character

/**
 * CharacterPose: Canonical pose and emotional state specification for the main dragon companion.
 * As defined in CHARACTER_BIBLE.md, this abstraction decouples UI features from raw asset names.
 */
enum class CharacterPose {
    IDLE,
    HAPPY,
    VERY_HAPPY,
    THINKING,
    CELEBRATING,
    DISAPPOINTED,
    ENCOURAGING,
    POINTING_LEFT,
    POINTING_RIGHT,
    POINTING_UP,
    POINTING_DOWN,
    READING,
    WRITING,
    LISTENING,
    SPEAKING,
    WALKING,
    RUNNING,
    JUMPING,
    SLEEPING,
    SURPRISED,
    CONFUSED,
    PROUD,
    WAVING,
    GAME_READY,
    HOLDING_REWARD,
    OPENING_CHEST
}

/**
 * Scale sizing buckets for companion mascot rendering in Compose.
 */
enum class CharacterScale {
    SMALL,   // 48–96 dp (HUD badges, compact list items, quick feedback)
    MEDIUM,  // 120–220 dp (Lesson cards, hint modals, practice feedback)
    LARGE    // 220–420+ dp (Home screen hero, Results victory panel, Profile)
}
