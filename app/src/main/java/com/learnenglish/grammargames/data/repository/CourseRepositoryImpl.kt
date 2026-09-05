package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.content.LearningContentDataSource
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.repository.CourseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val learningContentDataSource: LearningContentDataSource
) : CourseRepository {

    override fun observeCourses(): Flow<List<Course>> {
        return learningContentDataSource.getCourses()
    }
}
