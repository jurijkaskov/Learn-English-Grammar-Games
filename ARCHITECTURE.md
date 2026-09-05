# Learn English: Grammar Games — Architecture Guide

## 1. Overview & Architectural Goals

**Learn English: Grammar Games** is a scalable, offline-first Android application designed for mastering English grammar through pedagogical lessons, targeted drills, and interactive mini-games.

### Architectural Foundation
- **Modern Jetpack Compose & Material Design 3 (M3)** for declarative, accessible, and responsive user interfaces.
- **Android Navigation 3 (Nav3)** (`androidx.navigation3`) with type-safe serialized keys, explicit state list backstack, scoped ViewModels via `rememberViewModelStoreNavEntryDecorator()`, and modular `NavEntry` providers.
- **Pure Clean Architecture & MVVM / MVI State Flow**:
  - `UiState`: Immutable data representation of screen presentation.
  - `UiAction`: User actions and UI events sent to the ViewModel.
  - `ViewModel`: Scoped business controller exposing `StateFlow<UiState>`.
  - `Domain`: Pure Kotlin models, repository interfaces, and use cases (no Android framework imports).
  - `Data`: Room SQLite database, DataStore preferences, and repository implementations.
- **Hilt Dependency Injection** for modular, decoupled, and testable components.

---

## 2. Core Architectural Mandate (Golden Rules)

> ### ⚠️ **MANDATORY ARCHITECTURAL RULES**
> 1. **Navigation 3 Exclusivity**: Never introduce Navigation Compose 2.x (`NavController`, `NavHost`), global navigator singletons, or fragmented navigation backstacks. All navigation routes must be modeled in `AppNavKey` and coordinated via `AppNavigator` and `NavDisplay`.
> 2. **Pure Domain Layer**: Never introduce Android framework imports (`android.*`, `androidx.*`, Compose UI, Room entities) into the `domain` package. The domain layer must remain pure Kotlin.
> 3. **No Direct Entity Leaks**: Database entities (`UserProgressEntity`) and DataStore keys must never be exposed directly to the presentation layer. Always map to domain models through data mappers.
> 4. **No Cross-Feature ViewModels**: Each feature owns its own ViewModel. Features communicate purely through typed navigation keys or domain repositories.
> 5. **Lightweight Navigation Arguments**: Never pass complex models or entity objects through navigation keys. Only pass minimal primitive identifiers (e.g., `topicId: String`, `lessonId: String`).
> 6. **Consistent Component TestTags**: All interactive and primary UI elements must provide distinct `Modifier.testTag("...")` tags for robust testing and accessibility.

---

## 3. Package Structure & Module Hierarchy

```
com.learnenglish.grammargames/
├── app/
│   ├── GrammarGamesApplication.kt             # Hilt Application class
│   └── MainActivity.kt                        # Single Activity with edge-to-edge
├── core/
│   ├── common/
│   │   ├── Result.kt                          # AppResult<T> sealed interface
│   │   ├── error/                             # Sealed AppError hierarchy
│   │   └── dispatcher/                        # AppDispatchers & StandardDispatchers
│   ├── content/                               # Static offline grammar curriculum data source
│   ├── database/
│   │   ├── entity/                            # Room database entities
│   │   ├── dao/                               # Room DAOs with reactive Flow queries
│   │   └── GrammarGamesDatabase.kt            # Room database configuration
│   ├── datastore/                             # DataStore Preferences data sources & keys
│   ├── designsystem/
│   │   └── theme/                             # Color, Type, Shape, Dimens
│   └── navigation/
│       ├── AppNavKey.kt                       # Sealed interface for all navigation keys
│       ├── AppNavigator.kt                    # Navigation contract & DefaultAppNavigator
│       ├── DestinationConfig.kt               # Root tab & bottom bar visibility rules
│       ├── MainTab.kt                         # Bottom navigation bar enum & destinations
│       ├── NavigationState.kt                 # Compose SnapshotStateList backstack manager
│       ├── DemoNavigationFixtures.kt          # Shared constants for preview/mock routes
│       └── AppNavigation.kt                   # NavDisplay + Decorators + Animated BottomBar
├── di/                                        # Hilt dependency injection modules
├── domain/
│   ├── model/                                 # Pure business models (Course, Lesson, etc.)
│   ├── repository/                            # Repository contracts
│   └── usecase/                               # Focused use cases (ObserveCourses, AddXp)
├── data/
│   ├── mapper/                                # Entity ↔ Domain mappers
│   └── repository/                            # Concrete repository implementations
└── feature/
    ├── bootstrap/                             # App launch routing (onboarding vs main)
    │   ├── BootstrapViewModel.kt, BootstrapScreen.kt, BootstrapRoute.kt
    │   └── navigation/BootstrapNavEntries.kt
    ├── onboarding/                            # Multi-step personalization onboarding flow
    │   ├── presentation/
    │   │   ├── welcome/                       # App value proposition & start
    │   │   ├── goal/                          # Goal selection (exams, travel, fluency)
    │   │   ├── level/                         # Self-reported level (A1 to C1)
    │   │   ├── placement/                     # Quick placement diagnostic test
    │   │   ├── book/                          # Primary grammar textbook selection
    │   │   ├── dailygoal/                     # Daily practice duration target
    │   │   └── complete/                      # Personalized curriculum summary
    │   └── navigation/OnboardingNavEntries.kt
    ├── home/                                  # Main dashboard: streak, active course, actions
    │   ├── HomeViewModel.kt, HomeScreen.kt, HomeRoute.kt
    │   └── navigation/HomeNavEntries.kt
    ├── learn/                                 # Structured grammar syllabus & unit index
    │   ├── LearnViewModel.kt, LearnScreen.kt, LearnRoute.kt
    │   └── navigation/LearnNavEntries.kt
    ├── topic/                                 # Grammar topic detail (overview, lessons, tests)
    │   ├── TopicViewModel.kt, TopicScreen.kt, TopicRoute.kt
    │   └── navigation/TopicNavEntries.kt
    ├── lesson/                                # Interactive bite-sized grammar lesson
    │   ├── LessonViewModel.kt, LessonScreen.kt, LessonRoute.kt
    │   └── navigation/LessonNavEntries.kt
    ├── test/                                  # Topic evaluation & diagnostic test
    │   ├── TestViewModel.kt, TestScreen.kt, TestRoute.kt
    │   └── navigation/TestNavEntries.kt
    ├── games/                                 # Gamification arena & active game sessions
    │   ├── GamesHubViewModel.kt, GamesScreen.kt, GameSessionScreen.kt
    │   └── navigation/GamesNavEntries.kt
    ├── results/                               # Universal completion screen with XP breakdown
    │   ├── ResultsViewModel.kt, ResultsScreen.kt, ResultsRoute.kt
    │   └── navigation/ResultsNavEntries.kt
    ├── review/                                # Spaced repetition review deck
    │   ├── ReviewScreen.kt, ReviewRoute.kt
    │   └── navigation/ReviewNavEntries.kt
    ├── mistakes/                              # Error notebook & targeted drill library
    │   ├── MistakesViewModel.kt, MistakesScreen.kt, MistakesRoute.kt
    │   └── navigation/MistakesNavEntries.kt
    ├── profile/                               # Learner profile & daily goal adjustment
    │   ├── ProfileScreen.kt, ProfileRoute.kt
    │   ├── achievements/                      # Badges & milestone gallery
    │   ├── character/                         # Dragon companion stage & evolution perks
    │   └── navigation/ProfileNavEntries.kt
    └── settings/                              # Preferences (theme, audio, haptics, reminders)
        ├── SettingsViewModel.kt, SettingsScreen.kt, SettingsRoute.kt
        └── navigation/SettingsNavEntries.kt
```

---

## 4. Navigation 3 Architecture Details

### Destination Hierarchy (`AppNavKey`)

```
AppNavKey (Sealed Interface)
│
├── AppNavKey.Bootstrap
│
├── Onboarding Flow (Full-Screen)
│   ├── AppNavKey.Welcome
│   ├── AppNavKey.GoalSelection
│   ├── AppNavKey.LevelSelection
│   ├── AppNavKey.PlacementTest
│   ├── AppNavKey.BookSelection
│   ├── AppNavKey.DailyGoal
│   └── AppNavKey.OnboardingComplete
│
├── Main Root Tabs (Bottom Navigation Visible)
│   ├── AppNavKey.Home
│   ├── AppNavKey.Learn
│   ├── AppNavKey.Games
│   ├── AppNavKey.Review
│   └── AppNavKey.Profile
│
└── Detail & Immersive Destinations (Full-Screen)
    ├── AppNavKey.Topic(topicId)
    ├── AppNavKey.Lesson(topicId, lessonId)
    ├── AppNavKey.Test(topicId)
    ├── AppNavKey.GameSession(gameType, topicId?)
    ├── AppNavKey.Results(sessionId, resultType)
    ├── AppNavKey.Mistakes
    ├── AppNavKey.ReviewSession(reviewType)
    ├── AppNavKey.Achievements
    ├── AppNavKey.AchievementDetails(achievementId)
    ├── AppNavKey.Character
    ├── AppNavKey.CharacterCustomization
    └── AppNavKey.Settings
```

### Bottom Bar Visibility Matrix (`DestinationConfig.kt`)

| Destination Group | `shouldShowBottomBar()` | `isRootTab()` | `isFullScreen()` |
| :--- | :--- | :--- | :--- |
| **Root Tabs** (`Home`, `Learn`, `Games`, `Review`, `Profile`) | `true` | `true` | `false` |
| **Bootstrap** | `false` | `false` | `true` |
| **Onboarding Flow** (7 screens) | `false` | `false` | `true` |
| **Topic / Lesson / Test** | `false` | `false` | `true` |
| **Game Session & Results** | `false` | `false` | `true` |
| **Mistakes & Review Session** | `false` | `false` | `true` |
| **Achievements, Character, Settings** | `false` | `false` | `true` |

### Entry Provider Pattern
Each feature exports its own `*NavEntries.kt` function providing a `NavEntry<AppNavKey>`:
```kotlin
fun topicNavEntry(
    key: AppNavKey.Topic,
    navigator: AppNavigator
): NavEntry<AppNavKey> = NavEntry(key) {
    TopicRoute(
        topicId = key.topicId,
        onStartLesson = { lessonId -> navigator.navigateToLesson(key.topicId, lessonId) },
        onStartTest = { navigator.navigateToTest(key.topicId) },
        onBackClick = { navigator.navigateBack() }
    )
}
```

In `AppNavigation.kt`, the centralized `entryProvider` matches keys using pattern matching and delegates to feature modules, ensuring high cohesion and decoupling.

---

## 5. Screen & Feature Specification

| Screen | Route / Key | State & Actions | Primary TestTags | Navigation Transitions |
| :--- | :--- | :--- | :--- | :--- |
| **Bootstrap** | `AppNavKey.Bootstrap` | `BootstrapUiState` | `bootstrap_screen`, `bootstrap_progress` | -> `Home` (if completed) or `Welcome` |
| **Welcome** | `AppNavKey.Welcome` | Stateless | `welcome_screen`, `welcome_get_started_button` | -> `GoalSelection` |
| **Goal Selection** | `AppNavKey.GoalSelection` | `GoalSelectionUiState` | `goal_selection_screen`, `goal_continue_button` | -> `LevelSelection` |
| **Level Selection**| `AppNavKey.LevelSelection`| `LevelSelectionUiState`| `level_selection_screen`, `level_continue_button` | -> `PlacementTest` or `BookSelection` |
| **Placement Test** | `AppNavKey.PlacementTest` | `PlacementTestUiState` | `placement_test_screen`, `placement_submit_button`| -> `BookSelection` |
| **Book Selection** | `AppNavKey.BookSelection` | `BookSelectionUiState` | `book_selection_screen`, `book_continue_button` | -> `DailyGoal` |
| **Daily Goal** | `AppNavKey.DailyGoal` | `DailyGoalUiState` | `daily_goal_screen`, `daily_goal_continue_button` | -> `OnboardingComplete` |
| **Onboarding Complete** | `AppNavKey.OnboardingComplete` | `OnboardingCompleteUiState` | `onboarding_complete_screen`, `onboarding_start_button` | -> `finishOnboardingAndNavigateToHome()` |
| **Home** | `AppNavKey.Home` | `HomeUiState`, `HomeUiAction` | `home_screen`, `start_lesson_button`, `play_games_button` | -> `Topic`, `GameSession`, `Review` |
| **Learn** | `AppNavKey.Learn` | `LearnUiState` | `learn_screen`, `topic_card_<id>` | -> `Topic(topicId)` |
| **Topic Detail** | `AppNavKey.Topic(topicId)`| `TopicUiState` | `topic_screen`, `start_lesson_button`, `start_test_button` | -> `Lesson`, `Test` |
| **Lesson** | `AppNavKey.Lesson(topicId, lessonId)` | `LessonUiState` | `lesson_screen`, `lesson_finish_button` | -> `Results(sessionId, "LESSON")` |
| **Test** | `AppNavKey.Test(topicId)` | `TestUiState` | `test_screen`, `test_submit_button` | -> `Results(sessionId, "TEST")` |
| **Games Hub** | `AppNavKey.Games` | `GamesHubUiState` | `games_hub_screen`, `game_card_<type>` | -> `GameSession(gameType, topicId)` |
| **Game Session**| `AppNavKey.GameSession(...)`| `GameSessionUiState` | `game_session_screen`, `game_finish_button` | -> `Results(sessionId, "GAME")` |
| **Results** | `AppNavKey.Results(sessionId, type)`| `ResultsUiState` | `results_screen`, `results_continue_button` | -> `navigateAfterResults()` or `Mistakes` |
| **Review Deck** | `AppNavKey.Review` | Stateless / `ReviewItem` | `review_screen`, `start_review_button`, `review_mistakes_library_button` | -> `GameSession`, `Mistakes` |
| **Mistakes** | `AppNavKey.Mistakes` | `MistakesUiState` | `mistakes_screen`, `practice_mistake_button_<id>` | -> `GameSession` |
| **Profile** | `AppNavKey.Profile` | Stateless / Callbacks | `profile_screen`, `profile_achievements_card`, `profile_character_card` | -> `Achievements`, `Character`, `Settings` |
| **Achievements** | `AppNavKey.Achievements` | `AchievementsUiState` | `achievements_screen`, `achievement_item_<id>` | Back to `Profile` |
| **Character** | `AppNavKey.Character` | `CharacterUiState` | `character_screen`, `character_avatar_card` | Back to `Profile` |
| **Settings** | `AppNavKey.Settings` | `SettingsUiState`, `SettingsUiAction` | `settings_screen`, `settings_dark_theme_switch` | Back to `Profile` |
| **Curriculum Inspector** | `AppNavKey.CurriculumInspector` | `CurriculumInspectorUiState` | `curriculum_inspector_screen` | Back to `Settings` |

---

## 6. Grammar Curriculum Engine

The **Grammar Curriculum Engine** is the core pedagogical layer of the application, responsible for decoupling learning content from UI logic and user progress data.

### Structural Flow
```
JSON Assets (assets/curriculum/)
       │
       ▼
CurriculumLoader (Coroutine Dispatchers.IO deserializer via kotlinx.serialization)
       │
       ▼
CurriculumValidator (Validates ID uniqueness, graph cycles, foreign keys & syntax)
       │
       ▼
CurriculumIndex (In-memory indexed query engine: fast lookup by ID, topic, tags)
       │
       ▼
CurriculumRepository & Domain Use Cases
       │
       ▼
Feature ViewModels (Learn, Topic, Lesson, Game Session, Curriculum Inspector)
```

### Hierarchy & Domain Models
- **Course**: Level-grouped collection of grammar sections (`CourseId`, `CourseLevel`, `CefrLevel`).
- **GrammarSection**: Thematic cluster of topics (`SectionId`, `CourseId`).
- **GrammarTopic**: Canonical pedagogical unit with book cross-references (`TopicId`, `BookReference`).
- **Lesson**: Step within a topic containing learning objectives and activities (`LessonId`, `DifficultyLevel`).
- **Activity**: Granular activity step (`ActivityId`, `ActivityType`, `ActivityConfig`, `LessonContent`).
- **Question**: Polymorphic sealed question hierarchy (`MultipleChoiceQuestion`, `GapFillQuestion`, `SentenceBuilderQuestion`, `FindMistakeQuestion`, `TrueFalseQuestion`).

### Separation of Concerns
1. **Static Content**: Bundle JSON assets in `app/src/main/assets/curriculum/` defining grammar facts and questions.
2. **Dynamic Progress**: Tracked independently in Room database (`UserProgressEntity`, `LearningStatsEntity`) and DataStore.
3. **Purity**: Domain entities hold no dependencies on serialization DTOs, Room annotations, or Android platform types.

---

## 7. Testing Strategy

1. **Local JVM Robolectric Tests**: Fast headless integration tests running on the local JVM without emulator requirements.
2. **Navigation State Verification**: Unit tests verify:
   - Root tab transitions with clean stack reset.
   - Onboarding flow transitions and final dismissal.
   - Full-screen and modal transitions with two-step result popping.
   - Visibility contracts for bottom navigation.
3. **Roborazzi Screenshot Tests**: Visual regression tests validating UI layout consistency across screen densities.
