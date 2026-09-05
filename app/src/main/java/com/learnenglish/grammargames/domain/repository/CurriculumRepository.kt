package com.learnenglish.grammargames.domain.repository

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
import kotlinx.coroutines.flow.Flow

interface CurriculumRepository {
    fun observeCourses(): Flow<List<Course>>
    suspend fun getCourses(): List<Course>
    suspend fun getCourse(id: CourseId): Course?

    suspend fun getSectionsForCourse(courseId: CourseId): List<GrammarSection>
    suspend fun getSection(id: SectionId): GrammarSection?

    suspend fun getTopicsForSection(sectionId: SectionId): List<GrammarTopic>
    suspend fun getTopic(id: TopicId): GrammarTopic?

    suspend fun getLessonsForTopic(topicId: TopicId): List<Lesson>
    suspend fun getLesson(id: LessonId): Lesson?

    suspend fun getActivitiesForLesson(lessonId: LessonId): List<Activity>
    suspend fun getActivity(id: ActivityId): Activity?

    suspend fun getQuestionsForActivity(activityId: ActivityId): List<Question>
    suspend fun getQuestionsForTopic(topicId: TopicId, difficulty: DifficultyLevel? = null): List<Question>
    suspend fun getQuestionsByTag(tag: QuestionTag): List<Question>
    suspend fun getQuestion(id: QuestionId): Question?

    suspend fun getTopicLearningPath(topicId: TopicId): TopicLearningPath?
    suspend fun getTopicsForBookUnit(bookId: String, unitNumber: Int): List<GrammarTopic>
    suspend fun getLessonsForBookUnit(bookId: String, unitNumber: Int): List<Lesson>
    suspend fun getBookUnitsForTopic(topicId: TopicId): List<com.learnenglish.grammargames.domain.model.curriculum.CurriculumBookReference>
    suspend fun isCurriculumLoaded(): Boolean
    suspend fun reloadCurriculum()
}
