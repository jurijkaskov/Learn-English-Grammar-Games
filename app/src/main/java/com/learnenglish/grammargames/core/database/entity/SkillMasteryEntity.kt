package com.learnenglish.grammargames.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "skill_mastery",
    indices = [Index(value = ["topicId"])]
)
data class SkillMasteryEntity(
    @PrimaryKey
    val skillId: String,
    val topicId: String,
    val score: Int,
    val rawAccuracy: Float,
    val totalAttempts: Int,
    val successfulAttempts: Int,
    val confidence: Float,
    val lastPracticedTimestamp: Long?,
    val status: String,
    val decayFactor: Float,
    val algorithmVersion: Int
)
