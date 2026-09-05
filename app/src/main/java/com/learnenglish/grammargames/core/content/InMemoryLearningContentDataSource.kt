package com.learnenglish.grammargames.core.content

import com.learnenglish.grammargames.domain.model.BookReference
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.GrammarTopic
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Singleton
class InMemoryLearningContentDataSource @Inject constructor() : LearningContentDataSource {

    private val courses = listOf(
        Course(
            id = "essential_grammar",
            title = "Essential Grammar (A1-A2)",
            level = CourseLevel.BEGINNER,
            description = "Fundamental English grammar rules, basic tenses, and sentence building."
        ),
        Course(
            id = "intermediate_grammar",
            title = "Intermediate Grammar (B1-B2)",
            level = CourseLevel.INTERMEDIATE,
            description = "Complex tenses, modal verbs, conditionals, and passive voice."
        ),
        Course(
            id = "advanced_grammar",
            title = "Advanced Grammar (C1-C2)",
            level = CourseLevel.ADVANCED,
            description = "Inversion, subjunctive, subtle nuances, and mastery-level structures."
        )
    )

    private val topics = listOf(
        GrammarTopic(
            id = "present_simple",
            courseId = "essential_grammar",
            title = "Present Simple & Continuous",
            order = 1
        ),
        GrammarTopic(
            id = "past_simple",
            courseId = "essential_grammar",
            title = "Past Simple & Regular/Irregular Verbs",
            order = 2
        ),
        GrammarTopic(
            id = "present_perfect",
            courseId = "intermediate_grammar",
            title = "Present Perfect vs Past Simple",
            order = 1
        ),
        GrammarTopic(
            id = "conditionals",
            courseId = "intermediate_grammar",
            title = "Conditionals: 0, 1st, 2nd, and 3rd",
            order = 2
        ),
        GrammarTopic(
            id = "inversion",
            courseId = "advanced_grammar",
            title = "Inversion & Emphasis",
            order = 1
        )
    )

    private val bookReferences = listOf(
        BookReference(
            topicId = "present_simple",
            bookTitle = "English Grammar in Use (Murphy)",
            edition = "5th Edition",
            units = listOf(1, 2, 3, 4)
        ),
        BookReference(
            topicId = "present_perfect",
            bookTitle = "English Grammar in Use (Murphy)",
            edition = "5th Edition",
            units = listOf(7, 8, 13, 14)
        ),
        BookReference(
            topicId = "conditionals",
            bookTitle = "English Grammar in Use (Murphy)",
            edition = "5th Edition",
            units = listOf(38, 39, 40)
        )
    )

    override fun getCourses(): Flow<List<Course>> = flowOf(courses)

    override fun getTopics(courseId: String): Flow<List<GrammarTopic>> =
        flowOf(topics.filter { it.courseId == courseId })

    override fun getBookReferences(topicId: String): Flow<List<BookReference>> =
        flowOf(bookReferences.filter { it.topicId == topicId })
}
