package com.learnenglish.grammargames.domain.repository

import com.learnenglish.grammargames.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun observeCourses(): Flow<List<Course>>
}
