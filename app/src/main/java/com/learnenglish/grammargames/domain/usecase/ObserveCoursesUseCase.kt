package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.repository.CourseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveCoursesUseCase @Inject constructor(
    private val courseRepository: CourseRepository
) {
    operator fun invoke(): Flow<List<Course>> = courseRepository.observeCourses()
}
