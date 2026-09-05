package com.learnenglish.grammargames.domain.model.mastery

data class SectionMastery(
    val sectionId: String,
    val score: Int, // 0..100
    val status: MasteryStatus,
    val topicsMastery: List<TopicMastery> = emptyList()
)

data class CourseMastery(
    val courseId: String,
    val score: Int, // 0..100
    val status: MasteryStatus,
    val sectionsMastery: List<SectionMastery> = emptyList()
)
