package com.learnenglish.grammargames.core.content.curriculum.index

import com.learnenglish.grammargames.core.content.curriculum.loader.CurriculumContentBundle
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

class CurriculumIndex(bundle: CurriculumContentBundle) {

    val courses: List<Course> = bundle.courses
    val sections: List<GrammarSection> = bundle.sections
    val topics: List<GrammarTopic> = bundle.topics
    val lessons: List<Lesson> = bundle.lessons
    val activities: List<Activity> = bundle.activities
    val questions: List<Question> = bundle.questions
    val concepts: List<com.learnenglish.grammargames.domain.model.curriculum.GrammarConcept> = bundle.concepts
    val books: List<com.learnenglish.grammargames.domain.model.curriculum.GrammarBookCatalogItem> = bundle.books
    val report = bundle.report
    val manifest = bundle.manifest

    private val coursesById = courses.associateBy { it.id }
    private val sectionsById = sections.associateBy { it.id }
    private val topicsById = topics.associateBy { it.id }
    private val lessonsById = lessons.associateBy { it.id }
    private val activitiesById = activities.associateBy { it.id }
    private val questionsById = questions.associateBy { it.id }

    private val sectionsByCourseId = sections.groupBy { it.courseId }
    private val topicsBySectionId = topics.groupBy { it.sectionId }
    private val lessonsByTopicId = lessons.groupBy { it.topicId }
    private val activitiesByLessonId = activities.groupBy { it.lessonId }

    private val questionsByTopicId: Map<TopicId, List<Question>> = questions.groupBy { it.topicId }
    private val questionsByTag: Map<QuestionTag, List<Question>> = buildMap<QuestionTag, MutableList<Question>> {
        for (q in questions) {
            for (tag in q.tags) {
                getOrPut(tag) { mutableListOf() }.add(q)
            }
        }
    }

    private val topicsByBookUnit: Map<Pair<String, Int>, List<GrammarTopic>> = buildMap<Pair<String, Int>, MutableList<GrammarTopic>> {
        for (topic in topics) {
            for (ref in topic.bookReferences) {
                for (u in ref.units) {
                    getOrPut(ref.bookId.value to u) { mutableListOf() }.add(topic)
                }
            }
        }
    }

    fun getCourse(id: CourseId): Course? = coursesById[id]
    fun getSection(id: SectionId): GrammarSection? = sectionsById[id]
    fun getTopic(id: TopicId): GrammarTopic? = topicsById[id]
    fun getLesson(id: LessonId): Lesson? = lessonsById[id]
    fun getActivity(id: ActivityId): Activity? = activitiesById[id]
    fun getQuestion(id: QuestionId): Question? = questionsById[id]

    fun getTopicsForBookUnit(bookId: String, unitNumber: Int): List<GrammarTopic> =
        topicsByBookUnit[bookId to unitNumber] ?: emptyList()

    fun getLessonsForBookUnit(bookId: String, unitNumber: Int): List<Lesson> {
        val matchedTopics = getTopicsForBookUnit(bookId, unitNumber)
        return matchedTopics.flatMap { getLessonsForTopic(it.id) }
    }

    fun getBookUnitsForTopic(topicId: TopicId): List<com.learnenglish.grammargames.domain.model.curriculum.CurriculumBookReference> =
        getTopic(topicId)?.bookReferences ?: emptyList()

    fun getSectionsForCourse(courseId: CourseId): List<GrammarSection> =
        sectionsByCourseId[courseId]?.sortedBy { it.order } ?: emptyList()

    fun getTopicsForSection(sectionId: SectionId): List<GrammarTopic> =
        topicsBySectionId[sectionId]?.sortedBy { it.order } ?: emptyList()

    fun getLessonsForTopic(topicId: TopicId): List<Lesson> =
        lessonsByTopicId[topicId]?.sortedBy { it.order } ?: emptyList()

    fun getActivitiesForLesson(lessonId: LessonId): List<Activity> =
        activitiesByLessonId[lessonId]?.sortedBy { it.order } ?: emptyList()

    fun getQuestionsForActivity(activityId: ActivityId): List<Question> {
        val act = getActivity(activityId) ?: return emptyList()
        return act.questionIds.mapNotNull { questionsById[it] }
    }

    fun getQuestionsForTopic(topicId: TopicId, difficulty: DifficultyLevel? = null): List<Question> {
        val list = questionsByTopicId[topicId] ?: emptyList()
        return if (difficulty != null) list.filter { it.difficulty == difficulty } else list
    }

    fun getQuestionsByTag(tag: QuestionTag): List<Question> =
        questionsByTag[tag] ?: emptyList()
}
