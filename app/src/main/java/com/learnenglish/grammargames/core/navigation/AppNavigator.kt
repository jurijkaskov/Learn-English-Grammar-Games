package com.learnenglish.grammargames.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

interface AppNavigator {
    fun navigateTo(key: AppNavKey)
    fun navigateToRoot(key: AppNavKey)
    fun navigateBack(): Boolean
    fun finishOnboardingAndNavigateToHome()

    // Onboarding flow
    fun navigateToGoalSelection()
    fun navigateToLevelSelection()
    fun navigateToPlacementTest()
    fun navigateToBookSelection()
    fun navigateToDailyGoal()
    fun navigateToOnboardingComplete()

    // Features
    fun navigateToTopic(topicId: String)
    fun navigateToLesson(topicId: String, lessonId: String)
    fun navigateToTest(topicId: String)
    fun navigateToGameSession(gameType: String, topicId: String? = null)
    fun navigateToResults(sessionId: String, resultType: String = "LESSON")
    fun navigateToAchievements()
    fun navigateToAchievementDetails(achievementId: String)
    fun navigateToCharacter()
    fun navigateToCharacterCustomization()
    fun navigateToSettings()
    fun navigateToMistakes()
    fun navigateToReviewSession(reviewType: String = "MISTAKES")
    fun navigateAfterResults()
    fun navigateToCurriculumInspector()
}

class DefaultAppNavigator(
    private val navigationState: NavigationState
) : AppNavigator {

    override fun navigateTo(key: AppNavKey) {
        navigationState.navigateTo(key)
    }

    override fun navigateToRoot(key: AppNavKey) {
        navigationState.navigateToRoot(key)
    }

    override fun navigateBack(): Boolean {
        return navigationState.popBack()
    }

    override fun finishOnboardingAndNavigateToHome() {
        navigationState.finishOnboarding()
    }

    override fun navigateToGoalSelection() {
        navigationState.navigateTo(AppNavKey.GoalSelection)
    }

    override fun navigateToLevelSelection() {
        navigationState.navigateTo(AppNavKey.LevelSelection)
    }

    override fun navigateToPlacementTest() {
        navigationState.navigateTo(AppNavKey.PlacementTest)
    }

    override fun navigateToBookSelection() {
        navigationState.navigateTo(AppNavKey.BookSelection)
    }

    override fun navigateToDailyGoal() {
        navigationState.navigateTo(AppNavKey.DailyGoal)
    }

    override fun navigateToOnboardingComplete() {
        navigationState.navigateTo(AppNavKey.OnboardingComplete)
    }

    override fun navigateToTopic(topicId: String) {
        navigationState.navigateTo(AppNavKey.Topic(topicId))
    }

    override fun navigateToLesson(topicId: String, lessonId: String) {
        navigationState.navigateTo(AppNavKey.Lesson(topicId, lessonId))
    }

    override fun navigateToTest(topicId: String) {
        navigationState.navigateTo(AppNavKey.Test(topicId))
    }

    override fun navigateToGameSession(gameType: String, topicId: String?) {
        navigationState.navigateTo(AppNavKey.GameSession(gameType, topicId))
    }

    override fun navigateToResults(sessionId: String, resultType: String) {
        navigationState.navigateTo(AppNavKey.Results(sessionId, resultType))
    }

    override fun navigateToAchievements() {
        navigationState.navigateTo(AppNavKey.Achievements)
    }

    override fun navigateToAchievementDetails(achievementId: String) {
        navigationState.navigateTo(AppNavKey.AchievementDetails(achievementId))
    }

    override fun navigateToCharacter() {
        navigationState.navigateTo(AppNavKey.Character)
    }

    override fun navigateToCharacterCustomization() {
        navigationState.navigateTo(AppNavKey.CharacterCustomization)
    }

    override fun navigateToSettings() {
        navigationState.navigateTo(AppNavKey.Settings)
    }

    override fun navigateToMistakes() {
        navigationState.navigateTo(AppNavKey.Mistakes)
    }

    override fun navigateToReviewSession(reviewType: String) {
        navigationState.navigateTo(AppNavKey.ReviewSession(reviewType))
    }

    override fun navigateAfterResults() {
        // Pop Results and the underlying Test/GameSession/ReviewSession
        val backStack = navigationState.backStack
        val resultsIndex = backStack.indexOfLast { it is AppNavKey.Results }
        if (resultsIndex > 0) {
            val previous = backStack[resultsIndex - 1]
            if (previous is AppNavKey.Test || previous is AppNavKey.GameSession || previous is AppNavKey.Lesson || previous is AppNavKey.ReviewSession) {
                // Pop both Results and the completed session to return to the parent (e.g. Topic or Hub)
                backStack.removeAt(resultsIndex)
                backStack.removeAt(resultsIndex - 1)
                return
            }
        }
        navigationState.popBack()
    }

    override fun navigateToCurriculumInspector() {
        navigationState.navigateTo(AppNavKey.CurriculumInspector)
    }
}

@Composable
fun rememberAppNavigator(navigationState: NavigationState): AppNavigator {
    return remember(navigationState) { DefaultAppNavigator(navigationState) }
}
