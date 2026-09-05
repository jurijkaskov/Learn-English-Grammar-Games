package com.learnenglish.grammargames.di

import android.content.Context
import androidx.room.Room
import com.learnenglish.grammargames.core.database.GrammarGamesDatabase
import com.learnenglish.grammargames.core.database.dao.UserProgressDao
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
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserProgressDao(
        database: GrammarGamesDatabase
    ): UserProgressDao {
        return database.userProgressDao()
    }
}
