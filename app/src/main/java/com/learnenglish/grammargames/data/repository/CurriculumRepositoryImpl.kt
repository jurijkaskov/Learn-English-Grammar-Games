package com.learnenglish.grammargames.data.repository

import com.learnenglish.grammargames.core.content.curriculum.index.CurriculumIndex
import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumLoader
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
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Singleton
class CurriculumRepositoryImpl @Inject constructor(
    private val loader: CurriculumLoader
) : CurriculumRepository {

    private val mutex = Mutex()
    private var index: CurriculumIndex? = null

    private suspend fun getOrInitIndex(): CurriculumIndex {
        return index ?: mutex.withLock {
            index ?: run {
                val bundle = loader.loadCurriculum(forceReload = false)
                val newIndex = CurriculumIndex(bundle)
                index = newIndex
                newIndex
            }
        }
    }

    override fun observeCourses(): Flow<List<Course>> = flow {
        emit(getCourses())
    }

    override suspend fun getCourses(): List<Course> {
        return getOrInitIndex().courses
    }

    override suspend fun getCourse(id: CourseId): Course? {
        return getOrInitIndex().getCourse(id)
    }

    override suspend fun getSectionsForCourse(courseId: CourseId): List<GrammarSection> {
        return getOrInitIndex().getSectionsForCourse(courseId)
    }

    override suspend fun getSection(id: SectionId): GrammarSection? {
        return getOrInitIndex().getSection(id)
    }

    override suspend fun getTopicsForSection(sectionId: SectionId): List<GrammarTopic> {
        return getOrInitIndex().getTopicsForSection(sectionId)
    }

    override suspend fun getTopic(id: TopicId): GrammarTopic? {
        return getOrInitIndex().getTopic(id)
    }

    override suspend fun getLessonsForTopic(topicId: TopicId): List<Lesson> {
        return getOrInitIndex().getLessonsForTopic(topicId)
    }

    override suspend fun getLesson(id: LessonId): Lesson? {
        return getOrInitIndex().getLesson(id)
    }

    override suspend fun getActivitiesForLesson(lessonId: LessonId): List<Activity> {
        return getOrInitIndex().getActivitiesForLesson(lessonId)
    }

    override suspend fun getActivity(id: ActivityId): Activity? {
        return getOrInitIndex().getActivity(id)
    }

    override suspend fun getQuestionsForActivity(activityId: ActivityId): List<Question> {
        return getOrInitIndex().getQuestionsForActivity(activityId)
    }

    override suspend fun getQuestionsForTopic(topicId: TopicId, difficulty: DifficultyLevel?): List<Question> {
        return getOrInitIndex().getQuestionsForTopic(topicId, difficulty)
    }

    override suspend fun getQuestionsByTag(tag: QuestionTag): List<Question> {
        return getOrInitIndex().getQuestionsByTag(tag)
    }

    override suspend fun getQuestion(id: QuestionId): Question? {
        return getOrInitIndex().getQuestion(id)
    }

    override suspend fun getTopicLearningPath(topicId: TopicId): TopicLearningPath? {
        val currIndex = getOrInitIndex()
        val topic = currIndex.getTopic(topicId) ?: return null
        val lessons = currIndex.getLessonsForTopic(topicId)
        val activities = lessons.flatMap { currIndex.getActivitiesForLesson(it.id) }
        val questions = currIndex.getQuestionsForTopic(topicId)

        return TopicLearningPath(
            topic = topic,
            lessons = lessons,
            activitiesCount = activities.size,
            questionsCount = questions.size
        )
    }

    override suspend fun getTopicsForBookUnit(bookId: String, unitNumber: Int): List<GrammarTopic> {
        return getOrInitIndex().getTopicsForBookUnit(bookId, unitNumber)
    }

    override suspend fun getLessonsForBookUnit(bookId: String, unitNumber: Int): List<Lesson> {
        return getOrInitIndex().getLessonsForBookUnit(bookId, unitNumber)
    }

    override suspend fun getBookUnitsForTopic(topicId: TopicId): List<com.learnenglish.grammargames.domain.model.curriculum.CurriculumBookReference> {
        return getOrInitIndex().getBookUnitsForTopic(topicId)
    }

    override suspend fun isCurriculumLoaded(): Boolean {
        return index != null
    }

    override suspend fun reloadCurriculum() {
        mutex.withLock {
            val bundle = loader.loadCurriculum(forceReload = true)
            index = CurriculumIndex(bundle)
        }
    }
}
