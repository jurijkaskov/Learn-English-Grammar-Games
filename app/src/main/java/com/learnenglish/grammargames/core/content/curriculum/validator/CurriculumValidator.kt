package com.learnenglish.grammargames.core.content.curriculum.validator

import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.ActivityType
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.FindMistakeQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GapFillQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.MultipleChoiceQuestion
import com.learnenglish.grammargames.domain.model.curriculum.Question
import com.learnenglish.grammargames.domain.model.curriculum.SentenceBuilderQuestion
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.model.curriculum.TrueFalseQuestion

data class CurriculumValidationError(
    val entityType: String,
    val entityId: String,
    val message: String
)

data class CurriculumValidationWarning(
    val entityType: String,
    val entityId: String,
    val message: String
)

data class CurriculumValidationReport(
    val errors: List<CurriculumValidationError>,
    val warnings: List<CurriculumValidationWarning>
) {
    val isValid: Boolean get() = errors.isEmpty()
}

object CurriculumValidator {

    fun validate(
        courses: List<Course>,
        sections: List<GrammarSection>,
        topics: List<GrammarTopic>,
        lessons: List<Lesson>,
        activities: List<Activity>,
        questions: List<Question>
    ): CurriculumValidationReport {
        val errors = mutableListOf<CurriculumValidationError>()
        val warnings = mutableListOf<CurriculumValidationWarning>()

        // 1. Duplicate ID validation
        validateUniqueIds(courses.map { it.id.value }, "Course", errors)
        validateUniqueIds(sections.map { it.id.value }, "Section", errors)
        validateUniqueIds(topics.map { it.id.value }, "Topic", errors)
        validateUniqueIds(lessons.map { it.id.value }, "Lesson", errors)
        validateUniqueIds(activities.map { it.id.value }, "Activity", errors)
        validateUniqueIds(questions.map { it.id.value }, "Question", errors)

        val courseIds = courses.map { it.id }.toSet()
        val sectionIds = sections.map { it.id }.toSet()
        val topicIds = topics.map { it.id }.toSet()
        val lessonIds = lessons.map { it.id }.toSet()
        val activityIds = activities.map { it.id }.toSet()
        val questionMap = questions.associateBy { it.id }

        // 2. Reference validation
        sections.forEach { section ->
            if (section.courseId !in courseIds) {
                errors.add(
                    CurriculumValidationError("Section", section.id.value, "Course ${section.courseId.value} not found")
                )
            }
            section.topicIds.forEach { tId ->
                if (tId !in topicIds) {
                    errors.add(
                        CurriculumValidationError("Section", section.id.value, "Declared topic ${tId.value} not found")
                    )
                }
            }
        }

        topics.forEach { topic ->
            if (topic.sectionId !in sectionIds) {
                errors.add(
                    CurriculumValidationError("Topic", topic.id.value, "Section ${topic.sectionId.value} not found")
                )
            }
            topic.lessonIds.forEach { lId ->
                if (lId !in lessonIds) {
                    errors.add(
                        CurriculumValidationError("Topic", topic.id.value, "Declared lesson ${lId.value} not found")
                    )
                }
            }
            if (topic.title.isBlank()) {
                errors.add(CurriculumValidationError("Topic", topic.id.value, "Topic title is blank"))
            }
            if (topic.lessonIds.isEmpty()) {
                warnings.add(CurriculumValidationWarning("Topic", topic.id.value, "Topic has zero lessons"))
            }

            // Prerequisite validation
            topic.prerequisites.forEach { prereqId ->
                if (prereqId !in topicIds) {
                    errors.add(
                        CurriculumValidationError("Topic", topic.id.value, "Prerequisite topic ${prereqId.value} does not exist")
                    )
                }
                if (prereqId == topic.id) {
                    errors.add(
                        CurriculumValidationError("Topic", topic.id.value, "Topic cannot have itself as prerequisite")
                    )
                }
            }
        }

        // Circular prerequisites check
        validateCircularPrerequisites(topics, errors)

        lessons.forEach { lesson ->
            if (lesson.topicId !in topicIds) {
                errors.add(
                    CurriculumValidationError("Lesson", lesson.id.value, "Topic ${lesson.topicId.value} not found")
                )
            }
            lesson.activityIds.forEach { aId ->
                if (aId !in activityIds) {
                    errors.add(
                        CurriculumValidationError("Lesson", lesson.id.value, "Declared activity ${aId.value} not found")
                    )
                }
            }
            if (lesson.title.isBlank()) {
                errors.add(CurriculumValidationError("Lesson", lesson.id.value, "Lesson title is blank"))
            }
            if (lesson.activityIds.isEmpty()) {
                warnings.add(CurriculumValidationWarning("Lesson", lesson.id.value, "Lesson has zero activities"))
            }
        }

        activities.forEach { activity ->
            if (activity.lessonId !in lessonIds) {
                errors.add(
                    CurriculumValidationError("Activity", activity.id.value, "Lesson ${activity.lessonId.value} not found")
                )
            }
            if (activity.title.isBlank()) {
                errors.add(CurriculumValidationError("Activity", activity.id.value, "Activity title is blank"))
            }

            activity.questionIds.forEach { qId ->
                val q = questionMap[qId]
                if (q == null) {
                    errors.add(
                        CurriculumValidationError("Activity", activity.id.value, "Question ${qId.value} not found")
                    )
                } else {
                    // Type compatibility check
                    validateActivityQuestionCompatibility(activity, q, errors)
                }
            }

            if (activity.type != ActivityType.LESSON_CONTENT && activity.questionIds.isEmpty()) {
                warnings.add(CurriculumValidationWarning("Activity", activity.id.value, "Interactive activity has zero questions"))
            }
        }

        // 3. Questions integrity validation
        questions.forEach { q ->
            if (q.prompt.isBlank()) {
                errors.add(CurriculumValidationError("Question", q.id.value, "Question prompt is blank"))
            }
            if (q.topicId !in topicIds) {
                errors.add(CurriculumValidationError("Question", q.id.value, "Question topic ${q.topicId.value} does not exist"))
            }

            when (q) {
                is MultipleChoiceQuestion -> {
                    if (q.options.size < 2) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "Multiple choice must have at least 2 options"))
                    }
                    val optionIds = q.options.map { it.id }
                    if (optionIds.distinct().size != optionIds.size) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "Multiple choice has duplicate option IDs"))
                    }
                    if (q.options.none { it.id == q.correctOptionId }) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "correctOptionId ${q.correctOptionId} not found in options"))
                    }
                    if (q.options.any { it.text.isBlank() }) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "Multiple choice contains blank option text"))
                    }
                }
                is GapFillQuestion -> {
                    if (q.sentenceWithGaps.isBlank()) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "sentenceWithGaps is blank"))
                    }
                    if (q.correctAnswers.isEmpty() || q.correctAnswers.any { it.isBlank() }) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "correctAnswers cannot be empty or contain blank entries"))
                    }
                }
                is SentenceBuilderQuestion -> {
                    if (q.segments.size < 2) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "Sentence builder must have at least 2 segments"))
                    }
                    if (q.correctOrder.size != q.segments.size) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "correctOrder size must match segments size"))
                    }
                    val sortedIndices = q.correctOrder.sorted()
                    if (sortedIndices != q.segments.indices.toList()) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "correctOrder must be a valid permutation of segment indices"))
                    }
                }
                is FindMistakeQuestion -> {
                    if (q.sentenceParts.size < 2) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "Find mistake must have at least 2 sentence parts"))
                    }
                    if (q.incorrectPartIndex !in q.sentenceParts.indices) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "incorrectPartIndex is out of bounds"))
                    }
                    if (q.correction.isBlank()) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "correction cannot be blank"))
                    }
                }
                is TrueFalseQuestion -> {
                    if (q.statement.isBlank()) {
                        errors.add(CurriculumValidationError("Question", q.id.value, "statement is blank"))
                    }
                }
            }
        }

        return CurriculumValidationReport(errors = errors, warnings = warnings)
    }

    private fun validateUniqueIds(
        ids: List<String>,
        entityName: String,
        errors: MutableList<CurriculumValidationError>
    ) {
        val seen = mutableSetOf<String>()
        val duplicates = mutableSetOf<String>()
        for (id in ids) {
            if (!seen.add(id)) {
                duplicates.add(id)
            }
        }
        duplicates.forEach { dup ->
            errors.add(
                CurriculumValidationError(entityName, dup, "Duplicate $entityName ID: '$dup'")
            )
        }
    }

    private fun validateCircularPrerequisites(
        topics: List<GrammarTopic>,
        errors: MutableList<CurriculumValidationError>
    ) {
        val prereqMap = topics.associate { it.id to it.prerequisites }

        fun hasCycle(startId: TopicId, currentId: TopicId, visited: MutableSet<TopicId>): Boolean {
            if (currentId == startId && visited.isNotEmpty()) return true
            if (!visited.add(currentId)) return false

            val prereqs = prereqMap[currentId] ?: emptyList()
            for (p in prereqs) {
                if (p == startId || hasCycle(startId, p, visited)) return true
            }
            return false
        }

        for (topic in topics) {
            if (hasCycle(topic.id, topic.id, mutableSetOf())) {
                errors.add(
                    CurriculumValidationError("Topic", topic.id.value, "Circular prerequisite dependency detected for topic ${topic.id.value}")
                )
            }
        }
    }

    private fun validateActivityQuestionCompatibility(
        activity: Activity,
        question: Question,
        errors: MutableList<CurriculumValidationError>
    ) {
        when (activity.type) {
            ActivityType.MULTIPLE_CHOICE -> {
                if (question !is MultipleChoiceQuestion) {
                    errors.add(
                        CurriculumValidationError(
                            "Activity",
                            activity.id.value,
                            "Activity ${activity.id.value} of type MULTIPLE_CHOICE contains non-multiple-choice question ${question.id.value} (${question::class.simpleName})"
                        )
                    )
                }
            }
            ActivityType.GAP_FILL -> {
                if (question !is GapFillQuestion) {
                    errors.add(
                        CurriculumValidationError(
                            "Activity",
                            activity.id.value,
                            "Activity ${activity.id.value} of type GAP_FILL contains incompatible question ${question.id.value}"
                        )
                    )
                }
            }
            ActivityType.SENTENCE_BUILDER -> {
                if (question !is SentenceBuilderQuestion) {
                    errors.add(
                        CurriculumValidationError(
                            "Activity",
                            activity.id.value,
                            "Activity ${activity.id.value} of type SENTENCE_BUILDER contains incompatible question ${question.id.value}"
                        )
                    )
                }
            }
            ActivityType.FIND_MISTAKE -> {
                if (question !is FindMistakeQuestion) {
                    errors.add(
                        CurriculumValidationError(
                            "Activity",
                            activity.id.value,
                            "Activity ${activity.id.value} of type FIND_MISTAKE contains incompatible question ${question.id.value}"
                        )
                    )
                }
            }
            ActivityType.TRUE_FALSE -> {
                if (question !is TrueFalseQuestion) {
                    errors.add(
                        CurriculumValidationError(
                            "Activity",
                            activity.id.value,
                            "Activity ${activity.id.value} of type TRUE_FALSE contains incompatible question ${question.id.value}"
                        )
                    )
                }
            }
            else -> {
                // Generic drills, speed challenge, tests can combine multiple question types
            }
        }
    }
}
