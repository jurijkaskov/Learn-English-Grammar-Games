package com.learnenglish.grammargames.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_mastery")
data class TopicMasteryEntity(
    @PrimaryKey
    val topicId: String,
    val score: Int,
    val status: String,
    val skillsCount: Int,
    val masteredSkillsCount: Int,
    val lastPracticedTimestamp: Long?
)
