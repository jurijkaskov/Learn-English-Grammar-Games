# Learn English: Grammar Games — Unified Design System

## 1. Design Principles

Learn English: Grammar Games is designed as a **friendly, tactile, and highly legible educational game environment**. It balances pedagogical clarity with rewarding game-feel:

1. **Child-Friendly, Never Infantile**:
   - Curvature and typography are soft and organic, but clean and structured.
   - Appropriate and appealing for both younger learners (ages 8+) and adult learners.
   - Avoids noisy clashing rainbows, childish stickers, or unstructured layouts.

2. **Game-Feel with Purpose**:
   - Interactions feel rewarding through immediate visual feedback: subtle 0.98x tactile press depression, animated color shifts on answer choices, and clear celebration states.
   - Progression is tangible through mastery percentage bars, XP counters, fire streaks, and heart meters.

3. **High Pedagogical Legibility**:
   - High contrast between text and background across both Light and Dark themes.
   - Distinct visual separation between formulas (`GrammarFormulaText`), pedagogical examples (`GrammarExampleText`), translations, and interactive options.
   - Text scaling (accessibility font sizes) is respected everywhere without text truncation.

4. **Zero Hardcoded Values**:
   - Features must NEVER declare inline `Color(0xFF...)`, ad-hoc `RoundedCornerShape(X.dp)`, or arbitrary padding `padding(17.dp)`.
   - All visual decisions use tokens from `GrammarGamesColors`, `AppSpacing`, `AppShapes`, `AppDimensions`, `AppElevation`, and `AppMotion`.

---

## 2. Design Tokens Architecture

The design system is located under `com.learnenglish.grammargames.core.designsystem`:

```
core/designsystem/
├── theme/
│   ├── Color.kt             # Brand palette & GrammarGamesColors definition
│   ├── Type.kt              # M3 Typography scale & GrammarTypographyTokens
│   ├── Shape.kt             # Material3 Shapes & AppShapes tokens
│   ├── Spacing.kt           # Centralized 4dp/8dp grid AppSpacing tokens
│   ├── Dimensions.kt        # AppDimensions for touch targets, icons, heights
│   ├── Elevation.kt         # Soft elevation levels and borders
│   ├── Motion.kt            # AppMotion durations, springs, and press modifier
│   └── Theme.kt             # GrammarGamesTheme, Light & Dark color schemes
├── state/
│   └── LearningState.kt     # ExerciseAnswerState, LearningItemState, FeedbackType
├── component/
│   ├── button/              # GrammarPrimaryButton, Secondary, Tertiary, AnswerButton
│   ├── card/                # GrammarCard, LearningCard, GameCard, RuleCard, ExampleCard, HintCard
│   ├── chip/                # GrammarChip, GrammarBadge, GrammarStarRating
│   ├── feedback/            # GrammarFeedbackPanel, Correct/Wrong panels, StateViews
│   ├── navigation/          # GrammarTopAppBar
│   ├── panel/               # GrammarGamePanel, GrammarHud (XP, Streak, Hearts, Timer)
│   ├── progress/            # GrammarLinearProgress, Circular, Mastery, XP, DailyGoal
│   └── text/                # GrammarExampleText, GrammarFormulaText
├── preview/
│   └── DesignSystemPreviews.kt # Compose Previews for Light/Dark & states
└── showcase/
    └── DesignSystemShowcaseScreen.kt # Interactive developer QA showcase
```

---

## 3. Semantic Color Palette

Access semantic colors via `MaterialTheme.grammarGamesColors`:

| Token | Light Theme | Dark Theme | Purpose |
|---|---|---|---|
| `primaryAction` | Vibrant Purple `#6C5CE7` | Lavender Purple `#8875FF` | Primary CTAs, key accents, progress |
| `primaryActionContainer` | Soft Lilac `#F2EFFF` | Deep Purple Surface `#2B254E` | Card backgrounds, badge containers |
| `secondaryAction` | Ocean Blue `#2D9CDB` | Sky Cyan `#38B6FF` | Secondary drills, hints, info |
| `secondaryActionContainer` | Mist Blue `#EBF5FB` | Dark Cyan `#193344` | Secondary chips, info containers |
| `success` | Mint Emerald `#00B894` | Mint Green `#00D2A0` | Correct answers, completed units |
| `successContainer` | Soft Mint `#E8F8F5` | Dark Forest `#13382E` | Correct feedback banner & choice background |
| `warning` | Amber Flame `#F39C12` | Golden Sun `#F5B041` | Daily streak, warnings, hints |
| `warningContainer` | Warm Cream `#FEF9E7` | Dark Amber `#3D2E12` | Streak badges, hint cards |
| `error` | Coral Crimson `#E74C3C` | Coral Red `#FF6B6B` | Wrong answers, error states, low health |
| `errorContainer` | Rose Mist `#FDEDEC` | Dark Crimson `#3D1B1B` | Wrong feedback banner & choice background |
| `xp` | Trophy Gold `#F1C40F` | Bright Gold `#FFD700` | XP values, star ratings, coin rewards |
| `xpContainer` | Gold Tint `#FEFDE8` | Dark Gold `#3E3513` | XP pills, badge containers |
| `locked` | Muted Gray `#A0A5B5` | Slate Gray `#5D6275` | Locked padlocks, disabled borders |
| `lockedContainer` | Neutral Gray `#F0F2F6` | Deep Slate `#1E202B` | Locked unit cards, disabled answer buttons |
| `selected` | Vibrant Purple `#6C5CE7` | Lavender `#8875FF` | Active selection border |
| `selectedContainer` | Soft Lilac `#EFEBFF` | Midnight Indigo `#252147` | Selected multiple-choice background |

---

## 4. Typography Scale

Access standard typography via `MaterialTheme.typography` and specialized educational text styles via `GrammarTypographyTokens`:

- **Giant Scores & Results**: `displayLarge` (40sp bold)
- **Screen & Hub Headers**: `headlineLarge` (28sp bold), `headlineMedium` (24sp bold)
- **Cards & Section Titles**: `titleLarge` (20sp bold), `titleMedium` (17sp semibold)
- **Lesson Body & Explanations**: `bodyLarge` (16sp), `bodyMedium` (14sp)
- **Buttons & Interactive**: `labelLarge` (15sp bold), `labelMedium` (13sp semibold)
- **Grammar Formula**: `GrammarTypographyTokens.grammarFormula` (20sp bold, tracking 0.2sp)
- **Grammar Example**: `GrammarTypographyTokens.grammarExample` (17sp medium)

---

## 5. Spacing Scale (`AppSpacing`)

All spacing conforms to a rigid 4dp/8dp base rhythm:

| Token | Value | Standard Application |
|---|---|---|
| `none` | `0.dp` | Flush alignment |
| `xxxs` | `2.dp` | Sub-label offsets |
| `xxs` | `4.dp` | Inline icon-to-text spacing, tight gaps |
| `xs` | `8.dp` | Gaps between chips, small margins |
| `sm` | `12.dp` | Card inner element spacing |
| `md` | `16.dp` | Standard screen horizontal padding, card content padding |
| `lg` | `20.dp` | Large card padding, feedback top spacing |
| `xl` | `24.dp` | Section-to-section vertical separation |
| `xxl` | `32.dp` | Empty states, celebratory victory screens |
| `huge` | `48.dp` | Major visual breaks |

---

## 6. Shapes & Curvature (`AppShapes`)

Consistent, friendly corner geometry without harsh boxes:

- `AppShapes.buttonPrimary`: `18.dp`
- `AppShapes.buttonSecondary`: `16.dp`
- `AppShapes.buttonAnswer`: `16.dp`
- `AppShapes.cardBase`: `20.dp`
- `AppShapes.cardLearning`: `22.dp`
- `AppShapes.cardGame`: `24.dp`
- `AppShapes.panelGame`: `24.dp`
- `AppShapes.chip`: `999.dp` (Pill)
- `AppShapes.badge`: `8.dp`
- `AppShapes.progressBar`: `999.dp` (Pill)
- `AppShapes.bottomSheet`: Top corners `28.dp`

---

## 7. Interactive Components & Educational States

### Buttons
- **`GrammarPrimaryButton`**: 54dp min-height, prominent purple background, 0.98x press feedback, supports leading/trailing icons and animated loading spinner.
- **`GrammarSecondaryButton`**: 48dp min-height, light container with hairline border for secondary actions.
- **`GrammarTertiaryButton`**: Low-emphasis text action with guaranteed 48dp touch target.
- **`GrammarAnswerButton`**: Specialized choice button for exercises supporting all 5 pedagogical states:
  - `DEFAULT`: Neutral surface with subtle outline
  - `SELECTED`: Purple container with 2.5dp primary border
  - `CORRECT`: Emerald container, 2dp green border, animated checkmark icon
  - `WRONG`: Crimson container, 2dp red border, animated cross icon
  - `DISABLED`: Inactive gray container

### Cards
- **`GrammarCard`**: Base surface primitive with soft corners and hairline border.
- **`GrammarLearningCard`**: Units and syllabus items with status icons (CheckCircle, Lock).
- **`GrammarGameCard`**: Mini-games featuring difficulty badges, XP pill, and description.
- **`GrammarRuleCard`**: Formula highlight with colored container.
- **`GrammarExampleCard`**: Example sentence with optional audio speaker action.
- **`GrammarHintCard`**: Friendly advice with amber lightbulb icon.

### Progress & HUD
- **`GrammarLinearProgress`**: Safe clamping (0f..1f), 10dp height, rounded pill shape, smooth animation.
- **`GrammarCircularProgress`**: Rounded cap circular indicator.
- **`GrammarMasteryProgress`**: Topic progress with percentage text.
- **`GrammarXpProgress`**: Level and XP points with gold accent.
- **`GrammarDailyGoalProgress`**: Circular indicator showing daily goal fraction (e.g. 3 of 5).
- **`GrammarXpBadge`**, **`GrammarStreakBadge`**, **`GrammarHeartCounter`**, **`GrammarTimerBadge`**: Standardized HUD pills for game loops.

### Feedback
- **`GrammarFeedbackPanel`**: Bottom sheet banner displayed upon answer submission with supportive tone.
- **`GrammarCorrectFeedbackPanel`** and **`GrammarWrongFeedbackPanel`** convenience wrappers.
- **`GrammarEmptyState`**, **`GrammarLoadingState`**, **`GrammarErrorState`**.

---

## 8. Developer Guidelines for Creating New Features

When building a new feature or modifying an existing screen:

1. **Check `core/designsystem` First**:
   - Before writing any custom button, card, progress bar, or badge, check if `core/designsystem/component/` already provides it.
   - Use standard components to ensure visual consistency.

2. **Never Hardcode Visual Constants**:
   - ❌ `Modifier.padding(15.dp)` -> Use `Modifier.padding(AppSpacing.md)`
   - ❌ `Color(0xFF6C5CE7)` -> Use `MaterialTheme.grammarGamesColors.primaryAction`
   - ❌ `RoundedCornerShape(10.dp)` -> Use `AppShapes.medium` or `AppShapes.cardBase`

3. **Always Support Dark Theme**:
   - Always verify screens in both Light and Dark themes.
   - Use semantic roles (`onSurface`, `textSecondary`, `primaryActionContainer`) so colors adjust automatically.

4. **Accessibility First**:
   - All interactive elements must maintain a minimum touch target size of 48dp (`Modifier.minimumInteractiveComponentSize()` or `AppDimensions.minTouchTarget`).
   - Do not rely on color alone to indicate state: always provide an auxiliary icon or text label (such as checkmarks and crosses in `GrammarAnswerButton`).

5. **Visual QA with the Showcase**:
   - Run or preview `DesignSystemShowcaseScreen` to visually verify all tokens, state transitions, and interactive components.
