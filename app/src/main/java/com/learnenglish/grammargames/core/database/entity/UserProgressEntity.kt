package com.learnenglish.grammargames.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val id: Int = 1,
    val totalXp: Long = 0L,
    val level: Int = 1,
    val streakDays: Int = 0,
    val lastActiveTimestamp: Long = System.currentTimeMillis()
)
