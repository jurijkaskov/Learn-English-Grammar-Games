package com.learnenglish.grammargames.domain.model.mastery

data class TopicMastery(
    val topicId: String,
    val score: Int, // 0..100
    val status: MasteryStatus,
    val skillsMastery: List<MasterySkill> = emptyList(),
    val lastPracticedTimestamp: Long? = null
) {
    val masteredSkillsCount: Int get() = skillsMastery.count { it.score.status == MasteryStatus.MASTERED }
    val totalSkillsCount: Int get() = skillsMastery.size
}
