package com.learnenglish.grammargames.di

import com.learnenglish.grammargames.core.common.dispatcher.AppDispatchers
import com.learnenglish.grammargames.core.common.dispatcher.StandardDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CoroutineModule {

    @Binds
    @Singleton
    abstract fun bindAppDispatchers(
        standardDispatchers: StandardDispatchers
    ): AppDispatchers
}
