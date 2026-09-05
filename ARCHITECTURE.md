# Learn English: Grammar Games — Architecture Guide

## 1. Overview & Architectural Goals

**Learn English: Grammar Games** is a scalable, offline-first Android application designed for mastering English grammar through pedagogical lessons and interactive mini-games.

### Architectural Foundation
- **Modern Jetpack Compose & Material Design 3 (M3)** for declarative, accessible, and responsive user interfaces.
- **Android Navigation 3 (Nav3)** with typed keys, explicit state list backstack, and scoped view models via `NavDisplay`.
- **Pure Clean Architecture / MVVM Pattern** with unidirectional data flow (UDF) and strict dependency orientation:
  ```
  Presentation (Compose + ViewModel) ──► Domain (Pure Models, Repositories, UseCases) ◄── Data (Room, DataStore, Static Content)
  ```
- **Hilt Dependency Injection** for modular, testable, and loosely-coupled components.
- **Room Database & DataStore Preferences** for local persistence, offline-first operations, and fast reactive data streaming via Kotlin Coroutines and Flow.

---

## 2. Core Architectural Mandate (Golden Rule)

> ### ⚠️ **MANDATORY ARCHITECTURAL RULE**
> **Do not change or break the established architecture, navigation framework, package structure, or dependency direction merely to implement an individual feature or mini-game. Always extend and evolve the existing architecture instead.**

1. **No Ad-Hoc Navigation**: Never introduce Navigation Compose 2.x (`NavController`, `NavHost`), global navigator singletons, or fragmented navigation stacks. All navigation routes must be modeled in `AppNavKey` and handled through `NavigationState` and `NavDisplay`.
2. **Pure Domain Layer**: Never introduce Android framework imports (`android.*`, `androidx.*`, Compose UI, Room entities) into the `domain` package. The domain layer must remain pure Kotlin.
3. **No Direct Entity Leaks**: Database entities (`UserProgressEntity`) and DataStore keys must never be exposed directly to the presentation layer. Always map to domain models through data mappers.
4. **Unidirectional Data Flow**: State flows down via immutable `StateFlow<UiState>`, actions/events flow up via `UiAction`.

---

## 3. Package Structure & Responsibilities

```
com.learnenglish.grammargames/
├── app/
│   ├── GrammarGamesApplication.kt     # Hilt Application initialization
│   └── MainActivity.kt                # Root Android entry point with edge-to-edge
├── core/
│   ├── common/
│   │   ├── Result.kt                  # AppResult<T> sealed interface
│   │   ├── error/                     # Sealed AppError hierarchy
│   │   └── dispatcher/                # AppDispatchers abstraction & StandardDispatchers
│   ├── designsystem/
│   │   └── theme/                     # M3 Theme, Color, Type, Shape, Dimens
│   ├── content/                       # Static & offline grammar curriculum data source
│   ├── database/
│   │   ├── entity/                    # Room entities (e.g. UserProgressEntity)
│   │   ├── dao/                       # Room DAOs (e.g. UserProgressDao)
│   │   └── GrammarGamesDatabase.kt    # Room database declaration
│   ├── datastore/                     # DataStore Preferences data sources & keys
│   └── navigation/
│       ├── AppNavKey.kt               # Serializable sealed interface for all navigation keys
│       ├── NavigationState.kt         # SnapshotStateList backstack manager
│       ├── NavigationActions.kt       # Navigation callbacks contract
│       └── AppNavigation.kt           # NavDisplay + entry decorators + Bottom Navigation
├── di/                                # Hilt modules (CoroutineModule, DatabaseModule, etc.)
├── domain/
│   ├── model/                         # Pure business models (Course, UserProgress, etc.)
│   ├── repository/                    # Repository interfaces (CourseRepository, etc.)
│   └── usecase/                       # Focused, single-responsibility use cases
├── data/
│   ├── mapper/                        # Entity ↔ Domain mappers
│   └── repository/                    # Repository implementations
└── feature/
    ├── home/                          # Home dashboard, active course & XP stats
    ├── learn/                         # Structured grammar reference units
    ├── games/                         # Mini-game modes arena
    ├── review/                        # Spaced repetition review
    └── profile/                       # Learner settings and daily goals
```

---

## 4. Navigation Guidelines (Navigation 3)

1. **Routing Definition**: Every destination must be declared in `AppNavKey.kt`:
   ```kotlin
   @Serializable
   sealed interface AppNavKey {
       @Serializable data object Home : AppNavKey
       @Serializable data class LessonDetail(val topicId: String) : AppNavKey
   }
   ```
2. **Backstack Management**:
   The backstack is managed inside `NavigationState` as a Compose `SnapshotStateList<AppNavKey>`.
3. **UI Entry Point**:
   Use `NavDisplay`:
   ```kotlin
   NavDisplay(
       backStack = navigationState.backStack,
       entryProvider = entryProvider,
       entryDecorators = listOf(
           rememberSaveableStateHolderNavEntryDecorator(),
           rememberViewModelStoreNavEntryDecorator()
       ),
       onBack = { navigationState.popBack() }
   )
   ```
4. **ViewModel Scoping**:
   `rememberViewModelStoreNavEntryDecorator()` ensures that each `NavEntry` receives its own `ViewModelStoreOwner`. When an entry is popped from the backstack, its ViewModels are automatically cleared without memory leaks.

---

## 5. State Management & Presentation Guidelines

- **UI State**: Encapsulated in an immutable data class: `data class ExampleUiState(...)`.
- **UI Actions**: Encapsulated in a sealed interface: `sealed interface ExampleUiAction`.
- **ViewModel**: Exposes `StateFlow<ExampleUiState>` using `stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), initial)`.
- **Compose Testing & Accessibility**:
  - Every interactive element (buttons, cards, chips) **MUST** have a distinct `Modifier.testTag("...")`.
  - Every icon must have a descriptive `contentDescription`.
  - Minimum touch target size is 48.dp.
  - Spacing uses centralized `Dimens` tokens.

---

## 6. Data Storage & Persistence Flow

1. **Static Learning Curriculum**:
   Stored in `core/content/LearningContentDataSource.kt`. Contains pre-compiled grammar lessons, references (e.g. Murphy Units), explanations, and test templates.
2. **Dynamic User Progress (Room)**:
   Stored in SQLite via Room. Tracks earned XP, levels, streaks, mastered grammar rules, and game history. All read queries return `Flow<T>`.
3. **User Preferences (DataStore)**:
   Stored via `DataStore<Preferences>`. Stores selected track/course, daily goal duration, sound toggle, and study reminders.

---

## 7. Developer Checklists

### A. Adding a New Screen / Feature
1. [ ] Define a new `@Serializable` key in `AppNavKey`.
2. [ ] If needed, add domain models and use cases in `domain/`.
3. [ ] Create a feature package under `feature/<feature_name>/`.
4. [ ] Implement `<Feature>UiState` and `<Feature>UiAction`.
5. [ ] Implement `@HiltViewModel class <Feature>ViewModel`.
6. [ ] Implement stateless Composable `<Feature>Screen` with `Modifier.testTag`s and `@Preview`.
7. [ ] Implement `<Feature>Route` integrating the ViewModel and Screen.
8. [ ] Register the key in `AppNavigation.kt` inside `entryProvider`.
9. [ ] Write unit tests for ViewModel and use cases.

### B. Adding a New Mini-Game
1. [ ] Model game configuration in `domain/model/` (e.g. `GameMode`, `GameSessionResult`).
2. [ ] Define game progression rules in a dedicated UseCase (e.g. `EvaluateGameScoreUseCase`).
3. [ ] Connect game results to `AddUserXpUseCase` and `recordGrammarRuleResult` on completion.
4. [ ] Add game UI under `feature/games/` adhering to M3 styling and responsive layout.

### C. Adding a New Grammar Course / Curriculum Track
1. [ ] Add course definition and topics in `core/content/InMemoryLearningContentDataSource.kt`.
2. [ ] Map topics to corresponding reference literature (e.g., Murphy English Grammar In Use).
3. [ ] Verify that `ObserveCoursesUseCase` automatically streams the new course to UI.
