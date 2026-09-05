package com.learnenglish.grammargames.core.content.curriculum.validator

import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.ActivityType
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.FindMistakeQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GapFillQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GrammarBookCatalogItem
import com.learnenglish.grammargames.domain.model.curriculum.GrammarConcept
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

    fun validateThreeCourses(courses: List<Course>): List<CurriculumValidationError> {
        val errors = mutableListOf<CurriculumValidationError>()
        validateUniqueIds(courses.map { it.id.value }, "Course", errors)

        val beginnerCount = courses.count { it.level == CourseLevel.BEGINNER }
        val intermediateCount = courses.count { it.level == CourseLevel.INTERMEDIATE }
        val advancedCount = courses.count { it.level == CourseLevel.ADVANCED }

        if (beginnerCount != 1) {
            errors.add(CurriculumValidationError("Course", "all", "Expected exactly one BEGINNER course, found $beginnerCount"))
        }
        if (intermediateCount != 1) {
            errors.add(CurriculumValidationError("Course", "all", "Expected exactly one INTERMEDIATE course, found $intermediateCount"))
        }
        if (advancedCount != 1) {
            errors.add(CurriculumValidationError("Course", "all", "Expected exactly one ADVANCED course, found $advancedCount"))
        }

        courses.forEach { course ->
            if (course.cefrMin.ordinal > course.cefrMax.ordinal) {
                errors.add(
                    CurriculumValidationError(
                        "Course",
                        course.id.value,
                        "Invalid CEFR range: min (${course.cefrMin}) cannot exceed max (${course.cefrMax})"
                    )
                )
            }
            if (course.title.isBlank()) {
                errors.add(CurriculumValidationError("Course", course.id.value, "Course title is blank"))
            }
        }
        return errors
    }

    fun validate(
        courses: List<Course>,
        sections: List<GrammarSection>,
        topics: List<GrammarTopic>,
        lessons: List<Lesson>,
        activities: List<Activity>,
        questions: List<Question>,
        books: List<GrammarBookCatalogItem> = emptyList(),
        concepts: List<GrammarConcept> = emptyList(),
        strictCourseStructure: Boolean = false
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

        // Course structure checks
        if (strictCourseStructure || courses.size >= 3) {
            errors.addAll(validateThreeCourses(courses))
        } else {
            courses.forEach { course ->
                if (course.cefrMin.ordinal > course.cefrMax.ordinal) {
                    errors.add(
                        CurriculumValidationError(
                            "Course",
                            course.id.value,
                            "Invalid CEFR range: min (${course.cefrMin}) cannot exceed max (${course.cefrMax})"
                        )
                    )
                }
            }
        }

        courses.forEach { course ->
            course.sectionIds.forEach { sId ->
                if (sId !in sectionIds) {
                    errors.add(
                        CurriculumValidationError("Course", course.id.value, "Declared section ${sId.value} not found")
                    )
                }
            }

            // Section order uniqueness check within course
            val courseSections = sections.filter { it.courseId == course.id }
            val orders = courseSections.map { it.order }
            val duplicateOrders = orders.groupBy { it }.filter { it.value.size > 1 }.keys
            for (dupOrder in duplicateOrders) {
                errors.add(
                    CurriculumValidationError(
                        "Section",
                        course.id.value,
                        "Course ${course.id.value} contains duplicate section order: $dupOrder"
                    )
                )
            }
        }

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

        // Book Catalog validation
        val bookMap = books.associateBy { it.id.value }
        val conceptMap = concepts.associateBy { it.id.value }

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

            if (concepts.isNotEmpty() && topic.conceptId != null) {
                if (topic.conceptId.value !in conceptMap) {
                    errors.add(
                        CurriculumValidationError("Topic", topic.id.value, "Grammar concept ${topic.conceptId.value} not found in catalog")
                    )
                }
            }

            if (books.isNotEmpty()) {
                topic.bookReferences.forEach { ref ->
                    val book = bookMap[ref.bookId.value]
                    if (book == null) {
                        errors.add(
                            CurriculumValidationError("Topic", topic.id.value, "Book ${ref.bookId.value} not found in catalog")
                        )
                    } else if (!ref.editionId.isNullOrBlank()) {
                        val edition = book.editions.find { it.id == ref.editionId }
                        if (edition == null) {
                            errors.add(
                                CurriculumValidationError(
                                    "Topic",
                                    topic.id.value,
                                    "Book edition ${ref.editionId} not found in book ${ref.bookId.value}"
                                )
                            )
                        } else if (edition.totalUnits > 0) {
                            ref.units.forEach { unitNum ->
                                if (unitNum < 1 || unitNum > edition.totalUnits) {
                                    errors.add(
                                        CurriculumValidationError(
                                            "Topic",
                                            topic.id.value,
                                            "Unit $unitNum out of bounds for book ${ref.bookId.value} edition ${ref.editionId} (valid range: 1..${edition.totalUnits})"
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
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

    fun validateSkillWeights(skillWeights: List<Float>, topicId: String = "topic"): List<CurriculumValidationError> {
        val errors = mutableListOf<CurriculumValidationError>()
        if (skillWeights.any { it < 0f }) {
            errors.add(CurriculumValidationError("MasterySkill", topicId, "Skill weights must be non-negative"))
        }
        val sum = skillWeights.sum()
        if (sum <= 0f && skillWeights.isNotEmpty()) {
            errors.add(CurriculumValidationError("MasterySkill", topicId, "Sum of skill weights must be strictly positive"))
        }
        return errors
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

    fun calculateBookCoverage(
        bookId: String,
        editionId: String,
        topics: List<GrammarTopic>,
        totalUnits: Int
    ): BookMappingCoverageReport {
        val unitToTopics = mutableMapOf<Int, MutableList<String>>()
        val invalid = mutableSetOf<Int>()

        for (topic in topics) {
            for (ref in topic.bookReferences) {
                if (ref.bookId.value == bookId && (ref.editionId == null || ref.editionId == editionId)) {
                    for (u in ref.units) {
                        if (u in 1..totalUnits) {
                            unitToTopics.getOrPut(u) { mutableListOf() }.add(topic.id.value)
                        } else {
                            invalid.add(u)
                        }
                    }
                }
            }
        }

        val mapped = unitToTopics.keys.toSet()
        val unmapped = (1..totalUnits).filter { it !in mapped }.toSet()
        val multiMapped = unitToTopics.filter { it.value.size > 1 }.keys.toSet()

        return BookMappingCoverageReport(
            bookId = bookId,
            editionId = editionId,
            totalUnits = totalUnits,
            mappedUnits = mapped,
            unmappedUnits = unmapped,
            multiMappedUnits = multiMapped,
            invalidUnits = invalid
        )
    }

    fun validateBookMappingEntries(
        bookId: String,
        editionId: String,
        totalExpectedUnits: Int,
        entries: List<Triple<Int, String, String>>, // unitNumber, mappedTopicId, mappedSectionId
        existingTopicIds: Set<String>,
        existingSectionIds: Set<String>
    ): List<CurriculumValidationError> {
        val errors = mutableListOf<CurriculumValidationError>()
        val seenUnits = mutableSetOf<Int>()

        for ((unitNum, topicId, sectionId) in entries) {
            if (!seenUnits.add(unitNum)) {
                errors.add(CurriculumValidationError("BookMapping", "$bookId Unit $unitNum", "Duplicate unit number $unitNum in mapping"))
            }
            if (unitNum < 1 || unitNum > totalExpectedUnits) {
                errors.add(CurriculumValidationError("BookMapping", "$bookId Unit $unitNum", "Unit $unitNum out of bounds (1..$totalExpectedUnits)"))
            }
            if (topicId !in existingTopicIds) {
                errors.add(CurriculumValidationError("BookMapping", "$bookId Unit $unitNum", "Mapped topic '$topicId' does not exist in curriculum"))
            }
            if (sectionId !in existingSectionIds) {
                errors.add(CurriculumValidationError("BookMapping", "$bookId Unit $unitNum", "Mapped section '$sectionId' does not exist in curriculum"))
            }
        }

        val missingUnits = (1..totalExpectedUnits).filter { it !in seenUnits }
        if (missingUnits.isNotEmpty()) {
            errors.add(CurriculumValidationError("BookMapping", bookId, "Missing ${missingUnits.size} units in mapping: ${missingUnits.take(10)}..."))
        }

        return errors
    }
}

data class BookMappingCoverageReport(
    val bookId: String,
    val editionId: String,
    val totalUnits: Int,
    val mappedUnits: Set<Int>,
    val unmappedUnits: Set<Int>,
    val multiMappedUnits: Set<Int>,
    val invalidUnits: Set<Int>
) {
    val isComplete: Boolean get() = unmappedUnits.isEmpty() && invalidUnits.isEmpty()
    val coveragePercentage: Float get() = if (totalUnits > 0) (mappedUnits.size.toFloat() / totalUnits) * 100f else 0f
}

