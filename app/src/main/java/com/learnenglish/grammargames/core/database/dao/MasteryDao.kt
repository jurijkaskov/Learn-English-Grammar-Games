package com.learnenglish.grammargames.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.learnenglish.grammargames.core.database.entity.QuestionAttemptEntity
import com.learnenglish.grammargames.core.database.entity.SkillMasteryEntity
import com.learnenglish.grammargames.core.database.entity.TopicMasteryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MasteryDao {

    @Query("SELECT * FROM topic_mastery WHERE topicId = :topicId")
    fun observeTopicMastery(topicId: String): Flow<TopicMasteryEntity?>

    @Query("SELECT * FROM topic_mastery WHERE topicId = :topicId")
    suspend fun getTopicMastery(topicId: String): TopicMasteryEntity?

    @Query("SELECT * FROM topic_mastery")
    fun observeAllTopicMasteries(): Flow<List<TopicMasteryEntity>>

    @Query("SELECT * FROM topic_mastery")
    suspend fun getAllTopicMasteries(): List<TopicMasteryEntity>

    @Query("SELECT * FROM skill_mastery WHERE topicId = :topicId")
    fun observeSkillMasteriesForTopic(topicId: String): Flow<List<SkillMasteryEntity>>

    @Query("SELECT * FROM skill_mastery WHERE topicId = :topicId")
    suspend fun getSkillMasteriesForTopic(topicId: String): List<SkillMasteryEntity>

    @Query("SELECT * FROM skill_mastery")
    fun observeAllSkillMasteries(): Flow<List<SkillMasteryEntity>>

    @Query("SELECT * FROM question_attempts WHERE skillId = :skillId ORDER BY timestamp ASC")
    suspend fun getAttemptsForSkill(skillId: String): List<QuestionAttemptEntity>

    @Query("SELECT * FROM question_attempts WHERE topicId = :topicId ORDER BY timestamp ASC")
    suspend fun getAttemptsForTopic(topicId: String): List<QuestionAttemptEntity>

    @Query("SELECT * FROM question_attempts ORDER BY timestamp ASC")
    suspend fun getAllAttempts(): List<QuestionAttemptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuestionAttemptEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempts(attempts: List<QuestionAttemptEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSkillMasteries(skills: List<SkillMasteryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTopicMastery(topic: TopicMasteryEntity)

    @Query("DELETE FROM topic_mastery WHERE topicId = :topicId")
    suspend fun deleteTopicMastery(topicId: String)

    @Query("DELETE FROM skill_mastery WHERE topicId = :topicId")
    suspend fun deleteSkillMasteriesForTopic(topicId: String)

    @Query("DELETE FROM question_attempts WHERE topicId = :topicId")
    suspend fun deleteAttemptsForTopic(topicId: String)

    @Transaction
    suspend fun resetTopicMastery(topicId: String) {
        deleteTopicMastery(topicId)
        deleteSkillMasteriesForTopic(topicId)
        deleteAttemptsForTopic(topicId)
    }

    @Query("DELETE FROM topic_mastery")
    suspend fun deleteAllTopicMastery()

    @Query("DELETE FROM skill_mastery")
    suspend fun deleteAllSkillMastery()

    @Query("DELETE FROM question_attempts")
    suspend fun deleteAllAttempts()

    @Transaction
    suspend fun resetAllMastery() {
        deleteAllTopicMastery()
        deleteAllSkillMastery()
        deleteAllAttempts()
    }
}
