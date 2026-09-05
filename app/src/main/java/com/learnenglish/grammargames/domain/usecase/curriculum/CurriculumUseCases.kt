package com.learnenglish.grammargames.domain.usecase.curriculum

import com.learnenglish.grammargames.domain.model.curriculum.Activity
import com.learnenglish.grammargames.domain.model.curriculum.ActivityId
import com.learnenglish.grammargames.domain.model.curriculum.Course
import com.learnenglish.grammargames.domain.model.curriculum.CourseId
import com.learnenglish.grammargames.domain.model.curriculum.DifficultyLevel
import com.learnenglish.grammargames.domain.model.curriculum.GrammarSection
import com.learnenglish.grammargames.domain.model.curriculum.GrammarTopic
import com.learnenglish.grammargames.domain.model.curriculum.Lesson
import com.learnenglish.grammargames.domain.model.curriculum.LessonId
import com.learnenglish.grammargames.domain.model.curriculum.Question
import com.learnenglish.grammargames.domain.model.curriculum.QuestionId
import com.learnenglish.grammargames.domain.model.curriculum.QuestionTag
import com.learnenglish.grammargames.domain.model.curriculum.SectionId
import com.learnenglish.grammargames.domain.model.curriculum.TopicId
import com.learnenglish.grammargames.domain.model.curriculum.TopicLearningPath
import com.learnenglish.grammargames.domain.repository.CurriculumRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    operator fun invoke(): Flow<List<Course>> = repository.observeCourses()
    suspend fun getList(): List<Course> = repository.getCourses()
}

class GetSectionsForCourseUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(courseId: CourseId): List<GrammarSection> =
        repository.getSectionsForCourse(courseId)
}

class GetTopicsForSectionUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(sectionId: SectionId): List<GrammarTopic> =
        repository.getTopicsForSection(sectionId)
}

class GetTopicUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(topicId: TopicId): GrammarTopic? =
        repository.getTopic(topicId)
}

class GetLessonsForTopicUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(topicId: TopicId): List<Lesson> =
        repository.getLessonsForTopic(topicId)
}

class GetLessonUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(lessonId: LessonId): Lesson? =
        repository.getLesson(lessonId)
}

class GetActivitiesForLessonUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(lessonId: LessonId): List<Activity> =
        repository.getActivitiesForLesson(lessonId)
}

class GetQuestionsForActivityUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(activityId: ActivityId): List<Question> =
        repository.getQuestionsForActivity(activityId)
}

class GetQuestionsForTopicUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(topicId: TopicId, difficulty: DifficultyLevel? = null): List<Question> =
        repository.getQuestionsForTopic(topicId, difficulty)
}

class GetQuestionsByTagUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(tag: QuestionTag): List<Question> =
        repository.getQuestionsByTag(tag)
}

class GetTopicLearningPathUseCase @Inject constructor(
    private val repository: CurriculumRepository
) {
    suspend operator fun invoke(topicId: TopicId): TopicLearningPath? =
        repository.getTopicLearningPath(topicId)
}
