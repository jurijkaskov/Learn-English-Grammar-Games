package com.learnenglish.grammargames.di

import com.learnenglish.grammargames.core.content.InMemoryLearningContentDataSource
import com.learnenglish.grammargames.core.content.LearningContentDataSource
import com.learnenglish.grammargames.data.repository.BookCompanionRepositoryImpl
import com.learnenglish.grammargames.data.repository.CourseRepositoryImpl
import com.learnenglish.grammargames.data.repository.CurriculumRepositoryImpl
import com.learnenglish.grammargames.data.repository.UserPreferencesRepositoryImpl
import com.learnenglish.grammargames.data.repository.UserProgressRepositoryImpl
import com.learnenglish.grammargames.domain.repository.BookCompanionRepository
import com.learnenglish.grammargames.domain.repository.CourseRepository
import com.learnenglish.grammargames.domain.repository.CurriculumRepository
import com.learnenglish.grammargames.domain.repository.UserPreferencesRepository
import com.learnenglish.grammargames.domain.repository.UserProgressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLearningContentDataSource(
        impl: InMemoryLearningContentDataSource
    ): LearningContentDataSource

    @Binds
    @Singleton
    abstract fun bindCourseRepository(
        impl: CourseRepositoryImpl
    ): CourseRepository

    @Binds
    @Singleton
    abstract fun bindCurriculumRepository(
        impl: CurriculumRepositoryImpl
    ): CurriculumRepository

    @Binds
    @Singleton
    abstract fun bindUserProgressRepository(
        impl: UserProgressRepositoryImpl
    ): UserProgressRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindBookCompanionRepository(
        impl: BookCompanionRepositoryImpl
    ): BookCompanionRepository
}
