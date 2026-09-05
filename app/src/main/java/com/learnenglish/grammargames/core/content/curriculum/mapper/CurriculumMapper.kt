package com.learnenglish.grammargames.core.content.curriculum.mapper

import com.learnenglish.grammargames.core.content.curriculum.dto.ActivityConfigDto
import com.learnenglish.grammargames.core.content.curriculum.dto.ActivityDto
import com.learnenglish.grammargames.core.content.curriculum.dto.BookEditionDto
import com.learnenglish.grammargames.core.content.curriculum.dto.BookReferenceDto
import com.learnenglish.grammargames.core.content.curriculum.dto.CourseDto
import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarBookDto
import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarConceptDto
import com.learnenglish.grammargames.core.content.curriculum.dto.LearningObjectiveDto
import com.learnenglish.grammargames.core.content.curriculum.dto.LessonContentBlockDto
import com.learnenglish.grammargames.core.content.curriculum.dto.LessonDto
import com.learnenglish.grammargames.core.content.curriculum.dto.QuestionDto
import com.learnenglish.grammargames.core.content.curriculum.dto.SectionDto
import com.learnenglish.grammargames.core.content.curriculum.dto.TopicDto
import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.ActivityConfig
import com.learnenglish.grammargames.domain.model.curriculum.ActivityId
import com.learnenglish.grammargames.domain.model.curriculum.ActivityType
import com.learnenglish.grammargames.domain.model.curriculum.AnswerOption
import com.learnenglish.grammargames.domain.model.curriculum.ArtworkId
import com.learnenglish.grammargames.domain.model.curriculum.BookEdition
import com.learnenglish.grammargames.domain.model.curriculum.BookId
import com.learnenglish.grammargames.domain.model.curriculum.CefrLevel
import com.learnenglish.grammargames.domain.model.curriculum.ConceptDepth
import com.learnenglish.grammargames.domain.model.curriculum.ContentStatus
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.curriculum.CrosswordConfig
import com.learnenglish.grammargames.domain.model.curriculum.CurriculumBookReference
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.curriculum.FindMistakeQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GapFillQuestion
import com.learnenglish.grammargames.domain.model.curriculum.GrammarBookCatalogItem
import com.learnenglish.grammargames.domain.model.curriculum.GrammarConcept
import com.learnenglish.grammargames.domain.model.curriculum.GrammarConceptId
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.LearningObjective
import com.learnenglish.grammargames.domain.model.curriculum.LearningObjectiveId
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.LessonContent
import com.learnenglish.grammargames.domain.model.curriculum.LessonContentBlock
import com.learnenglish.grammargames.domain.model.curriculum.LessonId
import com.learnenglish.grammargames.domain.model.curriculum.MultipleChoiceQuestion
import com.learnenglish.grammargames.domain.model.curriculum.PracticeConfig
import com.learnenglish.grammargames.domain.model.curriculum.Question
import com.learnenglish.grammargames.domain.model.curriculum.QuestionId
import com.learnenglish.grammargames.domain.model.curriculum.QuestionTag
import com.learnenglish.grammargames.domain.model.curriculum.SectionId
import com.learnenglish.grammargames.domain.model.curriculum.SentenceBuilderQuestion
import com.learnenglish.grammargames.domain.model.curriculum.SpeedChallengeConfig
import com.learnenglish.grammargames.domain.model.curriculum.TestConfig
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.model.curriculum.TrueFalseQuestion
import com.learnenglish.grammargames.domain.model.curriculum.WordSearchConfig

object CurriculumMapper {

    fun mapCourse(dto: CourseDto): Course {
        val level = runCatching { CourseLevel.valueOf(dto.level.uppercase()) }
            .getOrDefault(CourseLevel.BEGINNER)
        val cefr = runCatching { CefrLevel.valueOf(dto.cefrLevel.uppercase()) }
            .getOrDefault(CefrLevel.A1)
        val cefrMin = dto.cefrMin?.let { runCatching { CefrLevel.valueOf(it.uppercase()) }.getOrNull() } ?: cefr
        val cefrMax = dto.cefrMax?.let { runCatching { CefrLevel.valueOf(it.uppercase()) }.getOrNull() } ?: cefr
        val status = runCatching { ContentStatus.valueOf(dto.status.uppercase()) }
            .getOrDefault(ContentStatus.ACTIVE)

        return Course(
            id = CourseId(dto.id),
            title = dto.title,
            level = level,
            description = dto.description,
            order = dto.order,
            sectionIds = dto.sectionIds.map { SectionId(it) },
            isEnabled = dto.isEnabled,
            cefrLevel = cefr,
            cefrMin = cefrMin,
            cefrMax = cefrMax,
            status = status
        )
    }

    fun mapSection(dto: SectionDto): GrammarSection {
        return GrammarSection(
            id = SectionId(dto.id),
            courseId = CourseId(dto.courseId),
            title = dto.title,
            description = dto.description,
            order = dto.order,
            topicIds = dto.topicIds.map { TopicId(it) }
        )
    }

    fun mapTopic(dto: TopicDto): GrammarTopic {
        val diff = runCatching { DifficultyLevel.valueOf(dto.difficulty.uppercase()) }
            .getOrDefault(DifficultyLevel.NORMAL)
        val cefr = runCatching { CefrLevel.valueOf(dto.cefrLevel.uppercase()) }
            .getOrDefault(CefrLevel.A1)
        val status = runCatching { ContentStatus.valueOf(dto.status.uppercase()) }
            .getOrDefault(ContentStatus.ACTIVE)
        val conceptDepth = dto.conceptDepth?.let { runCatching { ConceptDepth.valueOf(it.uppercase()) }.getOrNull() }

        return GrammarTopic(
            id = TopicId(dto.id),
            sectionId = SectionId(dto.sectionId),
            title = dto.title,
            shortDescription = dto.shortDescription,
            order = dto.order,
            lessonIds = dto.lessonIds.map { LessonId(it) },
            prerequisites = dto.prerequisites.map { TopicId(it) },
            difficulty = diff,
            cefrLevel = cefr,
            conceptId = dto.conceptId?.let { GrammarConceptId(it) },
            conceptDepth = conceptDepth,
            bookReferences = dto.bookReferences.map { mapBookReference(it) },
            artworkId = dto.artworkId?.let { ArtworkId(it) },
            status = status
        )
    }

    fun mapBookReference(dto: BookReferenceDto): CurriculumBookReference {
        return CurriculumBookReference(
            bookId = BookId(dto.bookId),
            bookTitle = dto.bookTitle,
            edition = dto.edition,
            editionId = dto.editionId,
            units = dto.units
        )
    }

    fun mapBookCatalogItem(dto: GrammarBookDto): GrammarBookCatalogItem {
        val level = runCatching { CourseLevel.valueOf(dto.targetLevel.uppercase()) }
            .getOrDefault(CourseLevel.BEGINNER)
        return GrammarBookCatalogItem(
            id = BookId(dto.id),
            title = dto.title,
            author = dto.author,
            targetLevel = level,
            editions = dto.editions.map { mapBookEdition(it) }
        )
    }

    private fun mapBookEdition(dto: BookEditionDto): BookEdition {
        return BookEdition(
            id = dto.id,
            editionName = dto.editionName,
            publicationYear = dto.publicationYear,
            totalUnits = dto.totalUnits
        )
    }

    fun mapGrammarConcept(dto: GrammarConceptDto): GrammarConcept {
        val introduced = runCatching { CourseLevel.valueOf(dto.introducedIn.uppercase()) }
            .getOrDefault(CourseLevel.BEGINNER)
        val mastered = runCatching { CourseLevel.valueOf(dto.masteredIn.uppercase()) }
            .getOrDefault(CourseLevel.ADVANCED)
        return GrammarConcept(
            id = GrammarConceptId(dto.id),
            canonicalName = dto.canonicalName,
            description = dto.description,
            category = dto.category,
            introducedIn = introduced,
            masteredIn = mastered
        )
    }

    fun mapLesson(dto: LessonDto): Lesson {
        val diff = runCatching { DifficultyLevel.valueOf(dto.difficulty.uppercase()) }
            .getOrDefault(DifficultyLevel.NORMAL)

        return Lesson(
            id = LessonId(dto.id),
            topicId = TopicId(dto.topicId),
            title = dto.title,
            order = dto.order,
            activityIds = dto.activityIds.map { ActivityId(it) },
            estimatedMinutes = dto.estimatedMinutes,
            difficulty = diff,
            learningObjectives = dto.learningObjectives.map { mapLearningObjective(it) }
        )
    }

    private fun mapLearningObjective(dto: LearningObjectiveDto): LearningObjective {
        return LearningObjective(
            id = LearningObjectiveId(dto.id),
            description = dto.description
        )
    }

    fun mapActivity(dto: ActivityDto): Activity {
        val type = runCatching { ActivityType.valueOf(dto.type.uppercase()) }
            .getOrDefault(ActivityType.MULTIPLE_CHOICE)

        val config = dto.config?.let { mapActivityConfig(it) }
        val lessonContent = dto.lessonContent?.let { mapLessonContent(it) }

        return Activity(
            id = ActivityId(dto.id),
            lessonId = LessonId(dto.lessonId),
            type = type,
            title = dto.title,
            order = dto.order,
            questionIds = dto.questionIds.map { QuestionId(it) },
            config = config,
            lessonContent = lessonContent
        )
    }

    private fun mapActivityConfig(dto: ActivityConfigDto): ActivityConfig {
        return when (dto) {
            is ActivityConfigDto.Practice -> PracticeConfig(
                shuffleQuestions = dto.shuffleQuestions,
                allowRetry = dto.allowRetry,
                showInstantExplanation = dto.showInstantExplanation
            )
            is ActivityConfigDto.Test -> TestConfig(
                questionCount = dto.questionCount,
                passThreshold = dto.passThreshold,
                timeLimitSeconds = dto.timeLimitSeconds,
                randomizeQuestions = dto.randomizeQuestions
            )
            is ActivityConfigDto.SpeedChallenge -> SpeedChallengeConfig(
                timeLimitSeconds = dto.timeLimitSeconds,
                questionCount = dto.questionCount,
                comboEnabled = dto.comboEnabled,
                bonusTimePerCorrectSec = dto.bonusTimePerCorrectSec
            )
            is ActivityConfigDto.Crossword -> CrosswordConfig(
                gridSize = dto.gridSize,
                wordCount = dto.wordCount,
                allowClueReveal = dto.allowClueReveal
            )
            is ActivityConfigDto.WordSearch -> WordSearchConfig(
                gridSize = dto.gridSize,
                wordCount = dto.wordCount,
                allowDiagonal = dto.allowDiagonal
            )
        }
    }

    private fun mapLessonContent(dto: com.learnenglish.grammargames.core.content.curriculum.dto.LessonContentDto): LessonContent {
        val blocks = dto.blocks.mapNotNull { blockDto ->
            when (blockDto.type.lowercase()) {
                "text" -> LessonContentBlock.Text(
                    id = blockDto.id,
                    markdownText = blockDto.text ?: ""
                )
                "rule" -> LessonContentBlock.Rule(
                    id = blockDto.id,
                    ruleTitle = blockDto.title ?: "",
                    ruleDescription = blockDto.description ?: ""
                )
                "formula" -> LessonContentBlock.Formula(
                    id = blockDto.id,
                    formulaPattern = blockDto.formulaPattern ?: "",
                    formulaNote = blockDto.formulaNote
                )
                "example" -> LessonContentBlock.Example(
                    id = blockDto.id,
                    sentence = blockDto.sentence ?: "",
                    highlightedPart = blockDto.highlightedPart,
                    translation = blockDto.translation
                )
                "mistake" -> LessonContentBlock.CommonMistake(
                    id = blockDto.id,
                    incorrectSentence = blockDto.incorrectSentence ?: "",
                    correctSentence = blockDto.correctSentence ?: "",
                    mistakeExplanation = blockDto.mistakeExplanation ?: ""
                )
                "tip" -> LessonContentBlock.Tip(
                    id = blockDto.id,
                    tipText = blockDto.text ?: ""
                )
                "note" -> LessonContentBlock.Note(
                    id = blockDto.id,
                    noteText = blockDto.text ?: ""
                )
                else -> null
            }
        }
        return LessonContent(blocks)
    }

    fun mapQuestion(dto: QuestionDto): Question {
        val diff = runCatching { DifficultyLevel.valueOf(dto.difficulty.uppercase()) }
            .getOrDefault(DifficultyLevel.NORMAL)
        val tags = dto.tags.map { QuestionTag(it) }.toSet()
        val objectives = dto.learningObjectiveIds.map { LearningObjectiveId(it) }

        return when (dto) {
            is QuestionDto.MultipleChoice -> MultipleChoiceQuestion(
                id = QuestionId(dto.id),
                topicId = TopicId(dto.topicId),
                difficulty = diff,
                prompt = dto.prompt,
                explanation = dto.explanation,
                hint = dto.hint,
                tags = tags,
                learningObjectiveIds = objectives,
                options = dto.options.map { AnswerOption(id = it.id, text = it.text) },
                correctOptionId = dto.correctOptionId,
                shuffleOptions = dto.shuffleOptions
            )
            is QuestionDto.GapFill -> GapFillQuestion(
                id = QuestionId(dto.id),
                topicId = TopicId(dto.topicId),
                difficulty = diff,
                prompt = dto.prompt,
                explanation = dto.explanation,
                hint = dto.hint,
                tags = tags,
                learningObjectiveIds = objectives,
                sentenceWithGaps = dto.sentenceWithGaps,
                correctAnswers = dto.correctAnswers,
                optionsPool = dto.optionsPool
            )
            is QuestionDto.SentenceBuilder -> SentenceBuilderQuestion(
                id = QuestionId(dto.id),
                topicId = TopicId(dto.topicId),
                difficulty = diff,
                prompt = dto.prompt,
                explanation = dto.explanation,
                hint = dto.hint,
                tags = tags,
                learningObjectiveIds = objectives,
                segments = dto.segments,
                correctOrder = dto.correctOrder,
                distractors = dto.distractors
            )
            is QuestionDto.FindMistake -> FindMistakeQuestion(
                id = QuestionId(dto.id),
                topicId = TopicId(dto.topicId),
                difficulty = diff,
                prompt = dto.prompt,
                explanation = dto.explanation,
                hint = dto.hint,
                tags = tags,
                learningObjectiveIds = objectives,
                sentenceParts = dto.sentenceParts,
                incorrectPartIndex = dto.incorrectPartIndex,
                correction = dto.correction
            )
            is QuestionDto.TrueFalse -> TrueFalseQuestion(
                id = QuestionId(dto.id),
                topicId = TopicId(dto.topicId),
                difficulty = diff,
                prompt = dto.prompt,
                explanation = dto.explanation,
                hint = dto.hint,
                tags = tags,
                learningObjectiveIds = objectives,
                statement = dto.statement,
                isTrue = dto.isTrue
            )
        }
    }
}
