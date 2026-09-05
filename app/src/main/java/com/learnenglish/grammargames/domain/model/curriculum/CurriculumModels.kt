package com.learnenglish.grammargames.domain.model.curriculum

import com.learnenglish.grammargames.domain.model.CourseLevel

data class CefrRange(
    val min: CefrLevel,
    val max: CefrLevel
)

data class LearningObjective(
    val id: LearningObjectiveId,
    val description: String
)

data class GrammarConcept(
    val id: GrammarConceptId,
    val canonicalName: String,
    val description: String = "",
    val category: String = "",
    val introducedIn: CourseLevel = CourseLevel.BEGINNER,
    val masteredIn: CourseLevel = CourseLevel.ADVANCED
)

data class BookEdition(
    val id: String,
    val editionName: String,
    val publicationYear: Int? = null,
    val totalUnits: Int
)

data class GrammarBookCatalogItem(
    val id: BookId,
    val title: String,
    val author: String,
    val targetLevel: CourseLevel,
    val editions: List<BookEdition> = emptyList()
)

data class CurriculumBookReference(
    val bookId: BookId,
    val bookTitle: String,
    val edition: String,
    val editionId: String? = null,
    val units: List<Int>
)

data class Activity(
    val id: ActivityId,
    val lessonId: LessonId,
    val type: ActivityType,
    val title: String,
    val order: Int,
    val questionIds: List<QuestionId>,
    val config: ActivityConfig? = null,
    val lessonContent: LessonContent? = null
)

data class Lesson(
    val id: LessonId,
    val topicId: TopicId,
    val title: String,
    val order: Int,
    val activityIds: List<ActivityId>,
    val estimatedMinutes: Int = 5,
    val difficulty: DifficultyLevel = DifficultyLevel.NORMAL,
    val learningObjectives: List<LearningObjective> = emptyList()
)

data class GrammarTopic(
    val id: TopicId,
    val sectionId: SectionId,
    val title: String,
    val shortDescription: String? = null,
    val order: Int,
    val lessonIds: List<LessonId>,
    val prerequisites: List<TopicId> = emptyList(),
    val difficulty: DifficultyLevel = DifficultyLevel.NORMAL,
    val cefrLevel: CefrLevel = CefrLevel.A1,
    val conceptId: GrammarConceptId? = null,
    val conceptDepth: ConceptDepth? = null,
    val bookReferences: List<CurriculumBookReference> = emptyList(),
    val artworkId: ArtworkId? = null,
    val status: ContentStatus = ContentStatus.ACTIVE
)

data class GrammarSection(
    val id: SectionId,
    val courseId: CourseId,
    val title: String,
    val description: String = "",
    val order: Int,
    val topicIds: List<TopicId>
)

data class Course(
    val id: CourseId,
    val title: String,
    val level: CourseLevel,
    val description: String = "",
    val order: Int = 1,
    val sectionIds: List<SectionId> = emptyList(),
    val isEnabled: Boolean = true,
    val cefrLevel: CefrLevel = CefrLevel.A1,
    val cefrMin: CefrLevel = cefrLevel,
    val cefrMax: CefrLevel = cefrLevel,
    val status: ContentStatus = ContentStatus.ACTIVE
) {
    val cefrRange: CefrRange get() = CefrRange(cefrMin, cefrMax)
}

data class TopicLearningPath(
    val topic: GrammarTopic,
    val lessons: List<Lesson>,
    val activitiesCount: Int,
    val questionsCount: Int
)
