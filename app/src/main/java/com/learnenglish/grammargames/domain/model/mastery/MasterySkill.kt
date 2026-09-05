package com.learnenglish.grammargames.domain.model.mastery

data class MasterySkill(
    val id: String,
    val topicId: String,
    val grammarConceptId: String? = null,
    val title: String,
    val description: String? = null,
    val order: Int = 1,
    val weight: Float = 1.0f,
    val learningObjectiveIds: List<String> = emptyList(),
    val score: MasteryScore = MasteryScore(0, 0f, 0f, 0, 0)
)
