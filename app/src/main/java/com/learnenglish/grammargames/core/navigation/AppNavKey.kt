package com.learnenglish.grammargames.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey {

    @Serializable
    data object Home : AppNavKey

    @Serializable
    data object Learn : AppNavKey

    @Serializable
    data object Games : AppNavKey

    @Serializable
    data object Review : AppNavKey

    @Serializable
    data object Profile : AppNavKey
}
