package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.domain.model.Course
import com.learnenglish.grammargames.domain.model.CourseLevel
import com.learnenglish.grammargames.domain.model.UserPreferences
import com.learnenglish.grammargames.domain.model.UserProgress
import com.learnenglish.grammargames.feature.home.HomeScreen
import com.learnenglish.grammargames.feature.home.HomeUiState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel8, sdk = [36])
class GreetingScreenshotTest {

  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun home_screen_screenshot() {
    composeTestRule.setContent {
      GrammarGamesTheme {
        HomeScreen(
          uiState = HomeUiState(
            isLoading = false,
            courses = listOf(
              Course(
                id = "essential_grammar",
                title = "Essential Grammar (A1-A2)",
                level = CourseLevel.BEGINNER,
                description = "Fundamental English grammar rules, basic tenses, and sentence building."
              )
            ),
            progress = UserProgress(totalXp = 120L, level = 2, streakDays = 4),
            preferences = UserPreferences(selectedCourseId = "essential_grammar")
          ),
          onAction = {}
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(filePath = "src/test/screenshots/home_screen.png")
  }
}

