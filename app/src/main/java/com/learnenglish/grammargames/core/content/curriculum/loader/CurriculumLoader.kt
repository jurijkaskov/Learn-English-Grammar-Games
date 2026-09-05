package com.learnenglish.grammargames.core.content.curriculum.loader

import android.content.Context
import android.util.Log
import com.learnenglish.grammargames.core.content.curriculum.dto.ActivityDto
import com.learnenglish.grammargames.core.content.curriculum.dto.CourseDto
import com.learnenglish.grammargames.core.content.curriculum.dto.CurriculumManifestDto
import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarBookDto
import com.learnenglish.grammargames.core.content.curriculum.dto.GrammarConceptDto
import com.learnenglish.grammargames.core.content.curriculum.dto.LessonDto
import com.learnenglish.grammargames.core.content.curriculum.dto.QuestionDto
import com.learnenglish.grammargames.core.content.curriculum.dto.SectionDto
import com.learnenglish.grammargames.core.content.curriculum.dto.TopicDto
import com.learnenglish.grammargames.core.content.curriculum.mapper.CurriculumMapper
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidationReport
import com.learnenglish.grammargames.core.content.curriculum.validator.CurriculumValidator
import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.GrammarBookCatalogItem
import com.learnenglish.grammargames.domain.model.curriculum.GrammarConcept
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.Question
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

data class CurriculumContentBundle(
    val manifest: CurriculumManifestDto,
    val courses: List<Course>,
    val sections: List<GrammarSection>,
    val topics: List<GrammarTopic>,
    val lessons: List<Lesson>,
    val activities: List<Activity>,
    val questions: List<Question>,
    val concepts: List<GrammarConcept> = emptyList(),
    val books: List<GrammarBookCatalogItem> = emptyList(),
    val report: CurriculumValidationReport
)

@Singleton
class CurriculumLoader @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = false
    }

    private var cachedBundle: CurriculumContentBundle? = null

    suspend fun loadCurriculum(forceReload: Boolean = false): CurriculumContentBundle = withContext(Dispatchers.IO) {
        if (!forceReload && cachedBundle != null) {
            return@withContext cachedBundle!!
        }

        val assetManager = context.assets
        val manifestJson = assetManager.open("curriculum/manifest.json").bufferedReader().use { it.readText() }
        val manifest = json.decodeFromString<CurriculumManifestDto>(manifestJson)

        val courses = mutableListOf<Course>()
        val sections = mutableListOf<GrammarSection>()
        val topics = mutableListOf<GrammarTopic>()
        val lessons = mutableListOf<Lesson>()
        val activities = mutableListOf<Activity>()
        val questions = mutableListOf<Question>()
        val concepts = mutableListOf<GrammarConcept>()
        val books = mutableListOf<GrammarBookCatalogItem>()

        // 0. Load shared catalogs
        val conceptsPath = "curriculum/shared/grammar_concepts.json"
        if (assetExists(conceptsPath)) {
            runCatching {
                val conceptsText = assetManager.open(conceptsPath).bufferedReader().use { it.readText() }
                val conceptDtos = json.decodeFromString<List<GrammarConceptDto>>(conceptsText)
                concepts.addAll(conceptDtos.map { CurriculumMapper.mapGrammarConcept(it) })
            }.onFailure { Log.w(TAG, "Failed loading grammar_concepts.json", it) }
        }

        val booksPath = "curriculum/shared/books.json"
        if (assetExists(booksPath)) {
            runCatching {
                val booksText = assetManager.open(booksPath).bufferedReader().use { it.readText() }
                val bookDtos = json.decodeFromString<List<GrammarBookDto>>(booksText)
                books.addAll(bookDtos.map { CurriculumMapper.mapBookCatalogItem(it) })
            }.onFailure { Log.w(TAG, "Failed loading books.json", it) }
        }

        for (courseDir in manifest.courses) {
            val baseCoursePath = "curriculum/$courseDir"

            // 1. Course
            val coursePath = "$baseCoursePath/course.json"
            if (assetExists(coursePath)) {
                val courseText = assetManager.open(coursePath).bufferedReader().use { it.readText() }
                val courseDto = json.decodeFromString<CourseDto>(courseText)
                courses.add(CurriculumMapper.mapCourse(courseDto))
            }

            // 2. Sections
            val sectionsPath = "$baseCoursePath/sections.json"
            if (assetExists(sectionsPath)) {
                val sectionsText = assetManager.open(sectionsPath).bufferedReader().use { it.readText() }
                val sectionDtos = json.decodeFromString<List<SectionDto>>(sectionsText)
                sections.addAll(sectionDtos.map { CurriculumMapper.mapSection(it) })
            }

            // 3. Topics
            val topicsPath = "$baseCoursePath/topics.json"
            if (assetExists(topicsPath)) {
                val topicsText = assetManager.open(topicsPath).bufferedReader().use { it.readText() }
                val topicDtos = json.decodeFromString<List<TopicDto>>(topicsText)
                topics.addAll(topicDtos.map { CurriculumMapper.mapTopic(it) })
            }

            // 4. Lessons
            val lessonsPath = "$baseCoursePath/lessons.json"
            if (assetExists(lessonsPath)) {
                val lessonsText = assetManager.open(lessonsPath).bufferedReader().use { it.readText() }
                val lessonDtos = json.decodeFromString<List<LessonDto>>(lessonsText)
                lessons.addAll(lessonDtos.map { CurriculumMapper.mapLesson(it) })
            }

            // 5. Activities
            val activitiesPath = "$baseCoursePath/activities.json"
            if (assetExists(activitiesPath)) {
                val activitiesText = assetManager.open(activitiesPath).bufferedReader().use { it.readText() }
                val activityDtos = json.decodeFromString<List<ActivityDto>>(activitiesText)
                activities.addAll(activityDtos.map { CurriculumMapper.mapActivity(it) })
            }

            // 6. Questions
            val questionsDir = "$baseCoursePath/questions"
            val questionFiles = runCatching { assetManager.list(questionsDir) }.getOrNull() ?: emptyArray()
            for (qFile in questionFiles) {
                if (qFile.endsWith(".json")) {
                    val qPath = "$questionsDir/$qFile"
                    val qText = assetManager.open(qPath).bufferedReader().use { it.readText() }
                    val qDtos = json.decodeFromString<List<QuestionDto>>(qText)
                    questions.addAll(qDtos.map { CurriculumMapper.mapQuestion(it) })
                }
            }
        }

        // Validate curriculum
        val report = CurriculumValidator.validate(
            courses = courses,
            sections = sections,
            topics = topics,
            lessons = lessons,
            activities = activities,
            questions = questions,
            books = books,
            concepts = concepts,
            strictCourseStructure = courses.size >= 3
        )

        Log.d(
            TAG,
            "Loaded Curriculum: ${courses.size} courses, ${sections.size} sections, " +
                "${topics.size} topics, ${lessons.size} lessons, ${activities.size} activities, " +
                "${questions.size} questions. Validation isValid=${report.isValid}, errors=${report.errors.size}"
        )

        val bundle = CurriculumContentBundle(
            manifest = manifest,
            courses = courses.sortedBy { it.order },
            sections = sections.sortedBy { it.order },
            topics = topics.sortedBy { it.order },
            lessons = lessons.sortedBy { it.order },
            activities = activities.sortedBy { it.order },
            questions = questions,
            concepts = concepts,
            books = books,
            report = report
        )
        cachedBundle = bundle
        bundle
    }

    private fun assetExists(path: String): Boolean {
        return runCatching {
            context.assets.open(path).close()
            true
        }.getOrDefault(false)
    }

    companion object {
        private const val TAG = "CurriculumLoader"
    }
}
