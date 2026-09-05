package com.learnenglish.grammargames.di

import android.content.Context
import androidx.room.Room
import com.learnenglish.grammargames.core.database.GrammarGamesDatabase
import com.learnenglish.grammargames.core.database.dao.MasteryDao
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
import com.learnenglish.grammargames.core.database.migrations.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): GrammarGamesDatabase {
        return Room.databaseBuilder(
            context,
            GrammarGamesDatabase::class.java,
            "grammar_games.db"
        ).addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserProgressDao(
        database: GrammarGamesDatabase
    ): UserProgressDao {
        return database.userProgressDao()
    }

    @Provides
    @Singleton
    fun provideMasteryDao(
        database: GrammarGamesDatabase
    ): MasteryDao {
        return database.masteryDao()
    }
}
