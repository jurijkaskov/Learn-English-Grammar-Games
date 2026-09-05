package com.learnenglish.grammargames.core.designsystem.showcase

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarAnswerButton
import com.learnenglish.grammargames.core.designsystem.component.character.CharacterPaletteTokens
import com.learnenglish.grammargames.core.designsystem.component.character.CharacterPose
import com.learnenglish.grammargames.core.designsystem.component.character.CharacterScale
import com.learnenglish.grammargames.core.designsystem.component.character.MainDragonCompanion
import com.learnenglish.grammargames.core.designsystem.component.scene.ArtPlaceholderCharacterSilhouette
import com.learnenglish.grammargames.core.designsystem.component.scene.ArtPlaceholderChest
import com.learnenglish.grammargames.core.designsystem.component.scene.ArtPlaceholderCloud
import com.learnenglish.grammargames.core.designsystem.component.scene.ArtPlaceholderLessonNode
import com.learnenglish.grammargames.core.designsystem.component.scene.ArtPlaceholderTree
import com.learnenglish.grammargames.core.designsystem.component.scene.ChestState
import com.learnenglish.grammargames.core.designsystem.component.scene.IllustratedScene
import com.learnenglish.grammargames.core.designsystem.component.scene.NodeVisualState
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarIconButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarPrimaryButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarSecondaryButton
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarTertiaryButton
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarExampleCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarGameCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarHintCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarLearningCard
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarRuleCard
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarBadge
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarChip
import com.learnenglish.grammargames.core.designsystem.component.chip.GrammarStarRating
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarCorrectFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarEmptyState
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarErrorState
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarLoadingState
import com.learnenglish.grammargames.core.designsystem.component.feedback.GrammarWrongFeedbackPanel
import com.learnenglish.grammargames.core.designsystem.component.navigation.GrammarTopAppBar
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarGamePanel
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarHeartCounter
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarStreakBadge
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarTimerBadge
import com.learnenglish.grammargames.core.designsystem.component.panel.GrammarXpBadge
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarDailyGoalProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarLinearProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarMasteryProgress
import com.learnenglish.grammargames.core.designsystem.component.progress.GrammarXpProgress
import com.learnenglish.grammargames.core.designsystem.component.text.GrammarExampleText
import com.learnenglish.grammargames.core.designsystem.component.text.GrammarFormulaText
import com.learnenglish.grammargames.core.designsystem.state.ExerciseAnswerState
import com.learnenglish.grammargames.core.designsystem.state.FeedbackType
import com.learnenglish.grammargames.core.designsystem.theme.AppDimensions
import com.learnenglish.grammargames.core.designsystem.theme.AppElevation
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * Visual Quality Assurance Showcase Screen for developer inspection and design review.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DesignSystemShowcaseScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var selectedAnswerIndex by remember { mutableStateOf(1) }
    var answerStateDemo by remember { mutableStateOf(ExerciseAnswerState.DEFAULT) }

    GrammarGamesTheme(darkTheme = isDarkTheme) {
        Scaffold(
            topBar = {
                GrammarTopAppBar(
                    title = "Design System Showcase",
                    onBackClick = onBackClick,
                    actions = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(end = AppSpacing.xs)
                        ) {
                            Text(
                                text = if (isDarkTheme) "Dark" else "Light",
                                style = MaterialTheme.typography.labelSmall
                            )
                            Spacer(modifier = Modifier.width(AppSpacing.xxs))
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isDarkTheme = it },
                                modifier = Modifier.testTag("showcase_theme_toggle")
                            )
                        }
                    },
                    testTag = "showcase_top_bar"
                )
            },
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(AppSpacing.screenHorizontalPhone),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.xl)
            ) {
                // SECTION 1: Color Palette
                ShowcaseSectionTitle("1. Brand & Semantic Palette")
                ColorSwatchesShowcase()

                // SECTION 2: Typography Scale
                ShowcaseSectionTitle("2. Typography Scale & Grammar Text")
                TypographyShowcase()

                // SECTION 3: Buttons & States
                ShowcaseSectionTitle("3. Buttons (Primary, Secondary, Tertiary, Icon)")
                ButtonShowcase()

                // SECTION 4: Interactive Answer Buttons
                ShowcaseSectionTitle("4. Answer Button States (Interactive)")
                AnswerStatesShowcase(
                    selectedIndex = selectedAnswerIndex,
                    onSelect = { selectedAnswerIndex = it }
                )

                // SECTION 5: Progress Indicators
                ShowcaseSectionTitle("5. Progress & Mastery Indicators")
                ProgressShowcase()

                // SECTION 6: HUD & Gamification Badges
                ShowcaseSectionTitle("6. HUD Indicators & Badges")
                HudShowcase()

                // SECTION 7: Reusable Cards
                ShowcaseSectionTitle("7. Cards (Learning, Game, Educational)")
                CardsShowcase()

                // SECTION 8: Game Panel & Canvas
                ShowcaseSectionTitle("8. Game Panel (Gameplay Surface)")
                GamePanelShowcase()

                // SECTION 9: Feedback Panels
                ShowcaseSectionTitle("9. Feedback Panels (Correct, Wrong, Info)")
                FeedbackShowcase()

                // SECTION 10: State Views
                ShowcaseSectionTitle("10. State Views (Empty, Loading, Error)")
                StateViewsShowcase()

                // SECTION 11: Art Direction Preview & Layering
                ShowcaseSectionTitle("11. Art Direction Preview (World & Layering Playground)")
                ArtDirectionPreviewShowcase()

                // SECTION 12: Character Bible Showcase (Main Dragon Companion)
                ShowcaseSectionTitle("12. Character Bible: Main Dragon Mascot (Locked Identity)")
                CharacterBibleShowcase()

                // SECTION 13: Graphic Assets Library
                ShowcaseSectionTitle("13. Graphic Assets System (GRAPHIC_ASSETS.md)")
                GraphicAssetsShowcase()

                Spacer(modifier = Modifier.height(AppSpacing.xxl))
            }
        }
    }
}

@Composable
private fun ShowcaseSectionTitle(title: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.grammarGamesColors.primaryAction
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = AppSpacing.xs),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColorSwatchesShowcase() {
    val colors = MaterialTheme.grammarGamesColors
    val swatches = listOf(
        "Primary" to colors.primaryAction,
        "Secondary" to colors.secondaryAction,
        "Success" to colors.success,
        "Warning" to colors.warning,
        "Error" to colors.error,
        "XP Gold" to colors.xp,
        "Locked" to colors.locked
    )

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs),
        verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)
    ) {
        swatches.forEach { (label, color) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(AppShapes.small)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, AppShapes.small)
                    .padding(horizontal = AppSpacing.xs, vertical = AppSpacing.xxs)
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(AppShapes.circle)
                        .background(color)
                )
                Spacer(modifier = Modifier.width(AppSpacing.xs))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
private fun TypographyShowcase() {
    GrammarCard(modifier = Modifier.fillMaxWidth()) {
        Text("Display Large — 40sp", style = MaterialTheme.typography.displayLarge)
        Text("Headline Large — 28sp", style = MaterialTheme.typography.headlineLarge)
        Text("Title Large — 20sp", style = MaterialTheme.typography.titleLarge)
        Text("Body Large — 16sp Standard body text for grammar lessons.", style = MaterialTheme.typography.bodyLarge)
        Text("Body Medium — 14sp Supporting notes and hints.", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(AppSpacing.xs))
        GrammarFormulaText("have / has + past participle")
        Spacer(modifier = Modifier.height(AppSpacing.xxs))
        GrammarExampleText(
            text = "She has already left for the airport.",
            highlightKeyword = "has already left"
        )
    }
}

@Composable
private fun ButtonShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
        GrammarPrimaryButton(
            text = "Primary CTA (Start Lesson)",
            leadingIcon = Icons.Default.PlayArrow,
            onClick = {}
        )
        GrammarSecondaryButton(
            text = "Secondary Button (Review Mistakes)",
            onClick = {}
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GrammarTertiaryButton(
                text = "Skip Exercise",
                onClick = {}
            )
            GrammarIconButton(
                icon = Icons.Default.Refresh,
                contentDescription = "Restart",
                onClick = {}
            )
        }
    }
}

@Composable
private fun AnswerStatesShowcase(
    selectedIndex: Int,
    onSelect: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)) {
        GrammarAnswerButton(
            text = "Default state option",
            optionLabel = "A",
            state = if (selectedIndex == 0) ExerciseAnswerState.SELECTED else ExerciseAnswerState.DEFAULT,
            onClick = { onSelect(0) }
        )
        GrammarAnswerButton(
            text = "Selected state option (Tapped)",
            optionLabel = "B",
            state = if (selectedIndex == 1) ExerciseAnswerState.SELECTED else ExerciseAnswerState.DEFAULT,
            onClick = { onSelect(1) }
        )
        GrammarAnswerButton(
            text = "Correct state option (Validated)",
            optionLabel = "C",
            state = ExerciseAnswerState.CORRECT,
            onClick = {}
        )
        GrammarAnswerButton(
            text = "Wrong state option (Validated)",
            optionLabel = "D",
            state = ExerciseAnswerState.WRONG,
            onClick = {}
        )
        GrammarAnswerButton(
            text = "Disabled / Inactive option",
            optionLabel = "E",
            state = ExerciseAnswerState.DISABLED,
            onClick = {}
        )
    }
}

@Composable
private fun ProgressShowcase() {
    GrammarCard(modifier = Modifier.fillMaxWidth()) {
        Text("Linear Progress (65%)", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(AppSpacing.xxs))
        GrammarLinearProgress(progress = 0.65f)
        Spacer(modifier = Modifier.height(AppSpacing.md))
        GrammarMasteryProgress(title = "Present Perfect Mastery", masteryPercentage = 78)
        Spacer(modifier = Modifier.height(AppSpacing.md))
        GrammarXpProgress(currentXp = 1450, targetXp = 2000, level = 5)
        Spacer(modifier = Modifier.height(AppSpacing.md))
        GrammarDailyGoalProgress(completed = 3, total = 5)
    }
}

@Composable
private fun HudShowcase() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
    ) {
        GrammarXpBadge(xp = 350)
        GrammarStreakBadge(streakDays = 7)
        GrammarHeartCounter(hearts = 4)
        GrammarTimerBadge(secondsRemaining = 24)
    }
}

@Composable
private fun CardsShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
        GrammarLearningCard(
            title = "Present Continuous",
            subtitle = "Unit 1 • 5 lessons",
            badgeText = "A1",
            isCompleted = true,
            onClick = {}
        )
        GrammarLearningCard(
            title = "Modal Verbs: Can, Could, May",
            subtitle = "Unit 12 • Locked",
            isLocked = true,
            onClick = {}
        )
        GrammarGameCard(
            title = "Speed Challenge",
            description = "Spot mistakes in under 60 seconds",
            xpReward = 50,
            difficultyLabel = "Fast",
            leadingIcon = Icons.Default.SportsEsports,
            onClick = {}
        )
        GrammarRuleCard(
            formula = "subject + have/has + past participle",
            explanation = "Use Present Perfect when connecting past events with present moments."
        )
        GrammarExampleCard(
            sentence = "They have lived here for ten years.",
            translation = "Они живут здесь десять лет.",
            highlightKeyword = "have lived",
            onAudioClick = {}
        )
        GrammarHintCard(
            tip = "Pro tip: Remember that irregular verbs have unique past participle forms (go -> went -> gone)."
        )
    }
}

@Composable
private fun GamePanelShowcase() {
    GrammarGamePanel {
        Text(
            text = "Which sentence uses the Present Perfect correctly?",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(AppSpacing.md))
        GrammarAnswerButton(
            text = "I have seen that movie yesterday.",
            optionLabel = "A",
            state = ExerciseAnswerState.DEFAULT,
            onClick = {}
        )
        Spacer(modifier = Modifier.height(AppSpacing.xs))
        GrammarAnswerButton(
            text = "I have already seen that movie.",
            optionLabel = "B",
            state = ExerciseAnswerState.CORRECT,
            onClick = {}
        )
    }
}

@Composable
private fun FeedbackShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
        GrammarCorrectFeedbackPanel(
            title = "Spot on! That's correct",
            description = "Present Perfect with 'already' emphasizes completion.",
            onContinueClick = {}
        )
        GrammarWrongFeedbackPanel(
            explanation = "Cannot use 'yesterday' with present perfect tense.",
            onContinueClick = {}
        )
    }
}

@Composable
private fun StateViewsShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
        GrammarEmptyState(
            title = "No Mistakes Saved",
            message = "You have clean scores on all drills so far!",
            actionText = "Practice New Topics",
            onActionClick = {}
        )
        GrammarLoadingState(label = "Generating your grammar diagnostic test...")
    }
}

@Composable
private fun ArtDirectionPreviewShowcase() {
    val colors = MaterialTheme.grammarGamesColors

    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.md)) {
        Text(
            text = "World Layering & Visual Formula: 70% Clean UI + 20% Cartoon Adventure + 10% Playful Decor",
            style = MaterialTheme.typography.bodySmall,
            color = colors.textSecondary
        )

        // Mini Illustrated Scene Preview with Layer Separation
        GrammarCard(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("art_direction_scene_card")
        ) {
            Text(
                text = "Scene Canvas (Grammar Journey Prototype)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(AppSpacing.xs))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(AppShapes.cardGame)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF81D4FA), Color(0xFFE1F5FE))
                        )
                    )
            ) {
                IllustratedScene(
                    modifier = Modifier.fillMaxSize(),
                    background = {
                        // Clouds in sky
                        ArtPlaceholderCloud(
                            scale = 55.dp,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = 16.dp, y = 12.dp)
                        )
                        ArtPlaceholderCloud(
                            scale = 70.dp,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = (-20).dp, y = 8.dp)
                        )
                    },
                    midground = {
                        // Rolling hill base
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(topStart = 80.dp, topEnd = 60.dp))
                                .background(Color(0xFF27AE60))
                        )
                        // Trees on hills
                        ArtPlaceholderTree(
                            height = 80.dp,
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .offset(x = 16.dp, y = (-50).dp)
                        )
                        ArtPlaceholderTree(
                            height = 70.dp,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (-24).dp, y = (-70).dp)
                        )
                    },
                    foregroundDecoration = {
                        // Winding path nodes
                        Row(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = 30.dp)
                                .fillMaxWidth()
                                .padding(horizontal = AppSpacing.sm),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ArtPlaceholderLessonNode(
                                number = 1,
                                state = NodeVisualState.COMPLETED
                            )
                            ArtPlaceholderLessonNode(
                                number = 2,
                                state = NodeVisualState.CURRENT
                            )
                            ArtPlaceholderLessonNode(
                                number = 3,
                                state = NodeVisualState.AVAILABLE
                            )
                            ArtPlaceholderLessonNode(
                                number = 4,
                                state = NodeVisualState.LOCKED
                            )
                            ArtPlaceholderChest(
                                state = ChestState.READY,
                                size = 48.dp
                            )
                        }

                        // Hero companion silhouette standing near current node
                        ArtPlaceholderCharacterSilhouette(
                            height = 80.dp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(x = (-36).dp, y = (-24).dp)
                        )
                    },
                    content = {
                        // Top UI overlay showing that educational content remains on top
                        Surface(
                            shape = AppShapes.cardBase,
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                            shadowElevation = AppElevation.level1,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(AppSpacing.sm)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(AppSpacing.sm),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "Lesson 2: Present Continuous",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Step 2 of 4 • +40 XP",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = colors.textSecondary
                                    )
                                }
                                GrammarPrimaryButton(
                                    text = "Start",
                                    onClick = {},
                                    modifier = Modifier.height(40.dp),
                                    testTag = "art_preview_start_lesson_button"
                                )
                            }
                        }
                    }
                )
            }
        }

        // Modular Asset Showcase
        GrammarCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Modular Asset Catalog (Vector Placeholders)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(AppSpacing.xs))

            Text(
                text = "Chest Progression States:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderChest(state = ChestState.CLOSED, size = 48.dp)
                    Text("Closed", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderChest(state = ChestState.READY, size = 48.dp)
                    Text("Ready", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderChest(state = ChestState.OPENED, size = 48.dp)
                    Text("Opened", style = MaterialTheme.typography.labelSmall)
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text(
                text = "Lesson Node Progression States:",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderLessonNode(number = 1, state = NodeVisualState.COMPLETED)
                    Text("Done", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderLessonNode(number = 2, state = NodeVisualState.CURRENT)
                    Text("Current", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderLessonNode(number = 3, state = NodeVisualState.AVAILABLE)
                    Text("Ready", style = MaterialTheme.typography.labelSmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ArtPlaceholderLessonNode(number = 4, state = NodeVisualState.LOCKED)
                    Text("Locked", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
private fun CharacterBibleShowcase() {
    var selectedPose by remember { mutableStateOf(CharacterPose.IDLE) }
    var selectedScale by remember { mutableStateOf(CharacterScale.MEDIUM) }

    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.md)) {
        // Explanatory card confirming identity lock per CHARACTER_BIBLE.md
        GrammarCard {
            Text(
                text = "Locked Identity: Main Dragon Mascot",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(AppSpacing.xs))
            Text(
                text = "Young adventurous green dragon (#58C96B), 8-unit proportions (42% head, 28% torso, 18% legs, 12% feet), warm amber-brown eyes, two cream horns (#F5DDA6), pale mint belly (#CFF1C5), signature royal purple backpack (#6C5CE7) & golden-yellow scarf (#F1C40F). Zero black outlines.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Interactive Mascot Stage
        GrammarCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Live Mascot Display (${selectedPose.name} • ${selectedScale.name})",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(AppSpacing.md))

                // Render MainDragonCompanion with currently selected pose and scale
                MainDragonCompanion(
                    pose = selectedPose,
                    scale = selectedScale
                )

                Spacer(modifier = Modifier.height(AppSpacing.md))

                // Scale Selector Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Scale:", style = MaterialTheme.typography.labelMedium)
                    CharacterScale.entries.forEach { scale ->
                        GrammarChip(
                            text = scale.name,
                            isSelected = selectedScale == scale,
                            onClick = { selectedScale = scale }
                        )
                    }
                }
            }
        }

        // Canonical Pose Selector Matrix
        GrammarCard(modifier = Modifier.fillMaxWidth()) {
            Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.sm)) {
                Text(
                    text = "Canonical Pose States (23 Canonical States Defined in Bible):",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )

                val corePoses = listOf(
                    CharacterPose.IDLE,
                    CharacterPose.HAPPY,
                    CharacterPose.VERY_HAPPY,
                    CharacterPose.THINKING,
                    CharacterPose.CELEBRATING,
                    CharacterPose.DISAPPOINTED,
                    CharacterPose.ENCOURAGING,
                    CharacterPose.READING,
                    CharacterPose.WRITING,
                    CharacterPose.LISTENING,
                    CharacterPose.RUNNING,
                    CharacterPose.JUMPING,
                    CharacterPose.SLEEPING,
                    CharacterPose.GAME_READY
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                ) {
                    corePoses.take(5).forEach { pose ->
                        GrammarChip(
                            text = pose.name.take(6),
                            isSelected = selectedPose == pose,
                            onClick = { selectedPose = pose }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                ) {
                    corePoses.drop(5).take(5).forEach { pose ->
                        GrammarChip(
                            text = pose.name.take(6),
                            isSelected = selectedPose == pose,
                            onClick = { selectedPose = pose }
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                ) {
                    corePoses.drop(10).forEach { pose ->
                        GrammarChip(
                            text = pose.name.take(6),
                            isSelected = selectedPose == pose,
                            onClick = { selectedPose = pose }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GraphicAssetsShowcase() {
    Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.md)) {
        GrammarCard(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
        ) {
            Text(
                text = "Graphic Assets System (GRAPHIC_ASSETS.md)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Canonical asset categories: Rewards, Economy, Game Mode Tiles, and Effects.",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text("Reward Chest States:", style = MaterialTheme.typography.labelMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.learnenglish.grammargames.core.designsystem.component.asset.ChestState.entries.forEach { state ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        com.learnenglish.grammargames.core.designsystem.component.asset.GraphicAssetResolver.RenderChest(
                            state = state,
                            size = 56.dp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(state.name.lowercase(), style = MaterialTheme.typography.labelSmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text("Economy Tokens (XP Star & Gold Coin):", style = MaterialTheme.typography.labelMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(AppSpacing.md),
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.learnenglish.grammargames.core.designsystem.component.asset.GraphicAssetResolver.RenderRewardToken(
                    type = com.learnenglish.grammargames.core.designsystem.component.asset.RewardAssetType.XP_STAR,
                    size = 48.dp
                )
                com.learnenglish.grammargames.core.designsystem.component.asset.GraphicAssetResolver.RenderRewardToken(
                    type = com.learnenglish.grammargames.core.designsystem.component.asset.RewardAssetType.COIN_GOLD,
                    size = 48.dp
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.sm))

            Text("Games Hub Art Tiles (Unified Palette & Dimensions):", style = MaterialTheme.typography.labelMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                com.learnenglish.grammargames.core.designsystem.component.asset.GameAssetType.entries.take(4).forEach { game ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        com.learnenglish.grammargames.core.designsystem.component.asset.GraphicAssetResolver.RenderGameArtTile(
                            gameType = game,
                            size = 52.dp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(game.name.take(5).lowercase(), style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

