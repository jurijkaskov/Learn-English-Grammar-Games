package com.learnenglish.grammargames.core.content

import com.learnenglish.grammargames.domain.model.BookReference
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.GrammarTopic
import kotlinx.coroutines.flow.Flow

interface LearningContentDataSource {
    fun getCourses(): Flow<List<Course>>
    fun getTopics(courseId: String): Flow<List<GrammarTopic>>
    fun getBookReferences(topicId: String): Flow<List<BookReference>>
}
