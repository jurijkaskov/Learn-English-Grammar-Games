package com.learnenglish.grammargames.data.mapper

import com.learnenglish.grammargames.core.database.entity.UserProgressEntity
import com.learnenglish.grammargames.domain.model.UserProgress

fun UserProgressEntity?.toDomain(): UserProgress {
    return if (this == null) {
        UserProgress(totalXp = 0L, level = 1, streakDays = 0)
    } else {
        UserProgress(
            totalXp = totalXp,
            level = level,
            streakDays = streakDays
        )
    }
}

fun UserProgress.toEntity(): UserProgressEntity {
    return UserProgressEntity(
        id = 1,
        totalXp = totalXp,
        level = level,
        streakDays = streakDays,
        lastActiveTimestamp = System.currentTimeMillis()
    )
}
