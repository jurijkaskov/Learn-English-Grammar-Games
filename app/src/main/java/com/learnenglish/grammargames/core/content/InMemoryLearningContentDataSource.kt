package com.learnenglish.grammargames.core.content

import com.learnenglish.grammargames.domain.model.BookReference
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.repository.CurriculumRepository
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class InMemoryLearningContentDataSource @Inject constructor(
    private val curriculumRepositoryProvider: Provider<CurriculumRepository>
) : LearningContentDataSource {

    private val fallbackCourses = listOf(
        Course(
            id = "course_beginner",
            title = "Beginner",
            level = CourseLevel.BEGINNER,
            description = "Build the grammar foundation required for basic everyday English."
        ),
        Course(
            id = "course_intermediate",
            title = "Intermediate",
            level = CourseLevel.INTERMEDIATE,
            description = "Develop confident control of core English grammar, contrast similar forms and handle more complex sentence patterns."
        ),
        Course(
            id = "course_advanced",
            title = "Advanced",
            level = CourseLevel.ADVANCED,
            description = "Develop precise, flexible and nuanced control of advanced grammar."
        )
    )

    override fun getCourses(): Flow<List<Course>> = flow {
        val repo = runCatching { curriculumRepositoryProvider.get() }.getOrNull()
        if (repo != null) {
            val curriculumCourses = runCatching { repo.getCourses() }.getOrNull()
            if (!curriculumCourses.isNullOrEmpty()) {
                emit(curriculumCourses.map {
                    Course(
                        id = it.id.value,
                        title = it.title,
                        level = it.level,
                        description = it.description
                    )
                })
                return@flow
            }
        }
        emit(fallbackCourses)
    }

    override fun getTopics(courseId: String): Flow<List<GrammarTopic>> = flow {
        val repo = runCatching { curriculumRepositoryProvider.get() }.getOrNull()
        if (repo != null) {
            val sections = runCatching { repo.getSectionsForCourse(CourseId(courseId)) }.getOrDefault(emptyList())
            val topics = sections.flatMap { sec ->
                runCatching { repo.getTopicsForSection(sec.id) }.getOrDefault(emptyList())
            }
            if (topics.isNotEmpty()) {
                emit(topics.map {
                    GrammarTopic(
                        id = it.id.value,
                        courseId = courseId,
                        title = it.title,
                        order = it.order
                    )
                })
                return@flow
            }
        }
        emit(emptyList())
    }

    override fun getBookReferences(topicId: String): Flow<List<BookReference>> = flow {
        val repo = runCatching { curriculumRepositoryProvider.get() }.getOrNull()
        if (repo != null) {
            val topic = runCatching { repo.getTopic(TopicId(topicId)) }.getOrNull()
            if (topic != null) {
                emit(topic.bookReferences.map {
                    BookReference(
                        topicId = topicId,
                        bookTitle = it.bookTitle,
                        edition = it.edition,
                        units = it.units
                    )
                })
                return@flow
            }
        }
        emit(emptyList())
    }
}
