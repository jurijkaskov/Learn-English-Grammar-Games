package com.learnenglish.grammargames.feature.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.component.button.GrammarSecondaryButton
import com.learnenglish.grammargames.core.designsystem.component.card.GrammarCard
import com.learnenglish.grammargames.core.designsystem.component.navigation.GrammarTopAppBar
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.GrammarGamesTheme
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

@Composable
fun SettingsScreen(
    state: SettingsUiState = SettingsUiState(),
    onAction: (SettingsUiAction) -> Unit = {},
    onBackClick: () -> Unit = {},
    onOpenShowcase: () -> Unit = {},
    onOpenCurriculumInspector: () -> Unit = {},
    onOpenBookCompanion: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen"),
        topBar = {
            GrammarTopAppBar(
                title = "Settings",
                onBackClick = onBackClick,
                testTag = "settings_top_bar"
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppSpacing.screenHorizontalPhone),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
        ) {
            item {
                Spacer(modifier = Modifier.height(AppSpacing.xxs))
                Text(
                    text = "Preferences",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                GrammarCard(modifier = Modifier.fillMaxWidth()) {
                    SettingToggleRow(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Theme",
                        subtitle = "Use dark surface palette",
                        checked = state.darkThemeEnabled,
                        onCheckedChange = { onAction(SettingsUiAction.ToggleDarkTheme(it)) },
                        testTag = "settings_dark_theme_switch"
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    SettingToggleRow(
                        icon = Icons.Default.VolumeUp,
                        title = "Sound Effects",
                        subtitle = "Audio feedback during game rounds",
                        checked = state.soundEffectsEnabled,
                        onCheckedChange = { onAction(SettingsUiAction.ToggleSound(it)) },
                        testTag = "settings_sound_switch"
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    SettingToggleRow(
                        icon = Icons.Default.Vibration,
                        title = "Haptic Feedback",
                        subtitle = "Vibrate on button taps and victory",
                        checked = state.hapticFeedbackEnabled,
                        onCheckedChange = { onAction(SettingsUiAction.ToggleHaptic(it)) },
                        testTag = "settings_haptic_switch"
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    SettingToggleRow(
                        icon = Icons.Default.Alarm,
                        title = "Daily Study Reminder",
                        subtitle = "Notification at 19:00 each day",
                        checked = state.dailyReminderEnabled,
                        onCheckedChange = { onAction(SettingsUiAction.ToggleReminder(it)) },
                        testTag = "settings_reminder_switch"
                    )
                }
            }

            // Textbook Companion Section
            item {
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                Text(
                    text = "Textbook Companion",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                GrammarCard(modifier = Modifier.fillMaxWidth().testTag("settings_book_companion_card")) {
                    Text(
                        text = "Active Grammar Textbook",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Text(
                        text = "Connect topics and interactive practice with units from Raymond Murphy and Martin Hewings.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.grammarGamesColors.textSecondary
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))

                    state.availableBooks.forEach { book ->
                        val edition = book.editions.firstOrNull()
                        val isSelected = state.selectedBookId == book.id
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = AppSpacing.xs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    if (edition != null) {
                                        onAction(SettingsUiAction.SelectBook(book.id, edition.id))
                                    }
                                },
                                modifier = Modifier.testTag("radio_book_${book.id}")
                            )
                            Spacer(modifier = Modifier.width(AppSpacing.xs))
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        if (edition != null) {
                                            onAction(SettingsUiAction.SelectBook(book.id, edition.id))
                                        }
                                    }
                            ) {
                                Text(
                                    text = book.title,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                Text(
                                    text = "${book.author} • ${book.targetLevel.name}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.grammarGamesColors.textSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    GrammarSecondaryButton(
                        text = "Browse Book Units",
                        leadingIcon = Icons.AutoMirrored.Filled.MenuBook,
                        onClick = onOpenBookCompanion,
                        testTag = "settings_open_book_companion_button"
                    )
                }
            }

            // Developer / QA Section
            item {
                Spacer(modifier = Modifier.height(AppSpacing.sm))
                Text(
                    text = "Developer & QA",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                GrammarCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Design System Showcase",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    Text(
                        text = "Visual QA screen inspecting all typography, palettes, interactive buttons, states, and educational cards.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.grammarGamesColors.textSecondary
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    GrammarSecondaryButton(
                        text = "Open Design Showcase",
                        leadingIcon = Icons.Default.BugReport,
                        onClick = onOpenShowcase,
                        testTag = "open_design_system_showcase_button"
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    GrammarSecondaryButton(
                        text = "Open Curriculum Inspector",
                        leadingIcon = Icons.Default.BugReport,
                        onClick = onOpenCurriculumInspector,
                        testTag = "open_curriculum_inspector_button"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(AppSpacing.xl))
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppSpacing.sm)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.grammarGamesColors.primaryAction
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.grammarGamesColors.textSecondary
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.testTag(testTag)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    GrammarGamesTheme {
        SettingsScreen()
    }
}
