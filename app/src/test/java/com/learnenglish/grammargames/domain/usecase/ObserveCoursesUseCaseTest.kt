package com.learnenglish.grammargames.domain.usecase

import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ObserveCoursesUseCaseTest {

    private class FakeCourseRepository : CourseRepository {
        val testCourses = listOf(
            Course(
                id = "c1",
                title = "Grammar 1",
                level = CourseLevel.BEGINNER,
                description = "Course 1 desc"
            ),
            Course(
                id = "c2",
                title = "Grammar 2",
                level = CourseLevel.INTERMEDIATE,
                description = "Course 2 desc"
            )
        )

        override fun observeCourses(): Flow<List<Course>> = flowOf(testCourses)
    }

    @Test
    fun observeCourses_returnsListOfCourses() = runTest {
        val fakeRepo = FakeCourseRepository()
        val useCase = ObserveCoursesUseCase(fakeRepo)

        val result = useCase().first()
        assertEquals(2, result.size)
        assertEquals("c1", result[0].id)
        assertEquals("c2", result[1].id)
    }
}
