package com.learnenglish.grammargames.feature.bookcompanion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing
import com.learnenglish.grammargames.core.designsystem.theme.Dimens
import com.learnenglish.grammargames.domain.model.book.BookUnitItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCompanionScreen(
    state: BookCompanionUiState,
    onAction: (BookCompanionUiAction) -> Unit,
    onNavigateToTopic: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Scroll to highlighted unit if requested
    LaunchedEffect(state.highlightUnitNumber, state.filteredUnits) {
        val targetUnit = state.highlightUnitNumber
        if (targetUnit != null) {
            val index = state.filteredUnits.indexOfFirst { it.unitNumber == targetUnit }
            if (index >= 0) {
                // Account for the header items (tabs, summary, search, filter chips)
                listState.animateScrollToItem(index + 4)
            }
        }
    }

    if (state.showCopyrightNotice) {
        AlertDialog(
            onDismissRequest = { onAction(BookCompanionUiAction.ToggleCopyrightNotice(false)) },
            title = { Text("Book Companion Policy") },
            text = {
                Text(
                    "This companion is an independent educational navigation tool linking curriculum topics with acclaimed grammar reference books (Essential Grammar in Use, English Grammar in Use, and Advanced Grammar in Use).\n\n" +
                        "Strict Copyright Compliance:\n" +
                        "• Does not contain or reproduce book exercises, tasks, explanations, or answers.\n" +
                        "• Displays only official unit titles and section categories for syllabus alignment.\n" +
                        "• Designed to accompany your physical or digital textbook."
                )
            },
            confirmButton = {
                TextButton(onClick = { onAction(BookCompanionUiAction.ToggleCopyrightNotice(false)) }) {
                    Text("Understood")
                }
            }
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("book_companion_screen"),
        topBar = {
            TopAppBar(
                title = { Text("Book Companion") },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("book_companion_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onAction(BookCompanionUiAction.ToggleCopyrightNotice(true)) },
                        modifier = Modifier.testTag("book_companion_info_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About Book Companion"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = Dimens.spacing16),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.md)
            ) {
                // 1. Books Tabs
                item {
                    Spacer(modifier = Modifier.height(AppSpacing.xxs))
                    val selectedBookIndex = state.availableBooks.indexOfFirst { it.id == state.selectedBookId }.coerceAtLeast(0)
                    if (state.availableBooks.isNotEmpty()) {
                        PrimaryTabRow(
                            selectedTabIndex = selectedBookIndex,
                            modifier = Modifier.fillMaxWidth().testTag("book_selector_tabs")
                        ) {
                            state.availableBooks.forEachIndexed { index, book ->
                                val edition = book.editions.firstOrNull()
                                Tab(
                                    selected = selectedBookIndex == index,
                                    onClick = {
                                        if (edition != null) {
                                            onAction(
                                                BookCompanionUiAction.SelectBookEdition(
                                                    bookId = book.id,
                                                    editionId = edition.id
                                                )
                                            )
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = when (book.id) {
                                                "essential_grammar_in_use" -> "Essential (A1-A2)"
                                                "english_grammar_in_use" -> "English (B1-B2)"
                                                "advanced_grammar_in_use" -> "Advanced (C1)"
                                                else -> book.title
                                            },
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                // 2. Active Book Overview Card
                item {
                    val mapping = state.currentMapping
                    if (mapping != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("book_companion_summary_card"),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Column(
                                modifier = Modifier.padding(Dimens.spacing16),
                                verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = mapping.bookTitle,
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Surface(
                                        shape = MaterialTheme.shapes.small,
                                        color = MaterialTheme.colorScheme.primary
                                    ) {
                                        Text(
                                            text = mapping.cefrRange,
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "By ${mapping.author} • ${mapping.edition} (${mapping.publicationYear})",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Verified,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "${mapping.totalUnits}/${mapping.totalUnits} Units Mapped (100% Complete)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Spacer(modifier = Modifier.height(AppSpacing.xxs))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    if (state.isSelectedBookUserActive) {
                                        Surface(
                                            shape = MaterialTheme.shapes.small,
                                            color = MaterialTheme.colorScheme.surface
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(16.dp)
                                                )
                                                Text(
                                                    text = "Your Active Textbook",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    } else {
                                        Button(
                                            onClick = { onAction(BookCompanionUiAction.SetAsActiveBook) },
                                            modifier = Modifier.testTag("set_active_book_button")
                                        ) {
                                            Text("Set as Active Textbook")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. Search Field
                item {
                    OutlinedTextField(
                        value = state.searchQuery,
                        onValueChange = { onAction(BookCompanionUiAction.SetSearchQuery(it)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("book_companion_search_input"),
                        placeholder = { Text("Search by unit number, title, or topic...") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        },
                        trailingIcon = {
                            if (state.searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onAction(BookCompanionUiAction.SetSearchQuery("")) }) {
                                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )
                }

                // 4. Section Filter Chips
                item {
                    if (state.availableSections.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                        ) {
                            FilterChip(
                                selected = state.selectedSectionFilter == null,
                                onClick = { onAction(BookCompanionUiAction.SelectSectionFilter(null)) },
                                label = { Text("All (${state.currentMapping?.units?.size ?: 0})") },
                                modifier = Modifier.testTag("section_filter_all")
                            )
                            state.availableSections.forEach { section ->
                                val count = state.currentMapping?.units?.count { it.bookSection == section } ?: 0
                                FilterChip(
                                    selected = state.selectedSectionFilter == section,
                                    onClick = { onAction(BookCompanionUiAction.SelectSectionFilter(section)) },
                                    label = { Text("$section ($count)") }
                                )
                            }
                        }
                    }
                }

                // 5. Units Header
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Units (${state.filteredUnits.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        if (state.highlightUnitNumber != null) {
                            TextButton(onClick = { onAction(BookCompanionUiAction.DismissHighlight) }) {
                                Text("Clear Highlight")
                            }
                        }
                    }
                }

                // 6. Units List
                if (state.filteredUnits.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = AppSpacing.xl),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No units matching '${state.searchQuery}'",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(state.filteredUnits, key = { it.unitNumber }) { unit ->
                        val isHighlighted = state.highlightUnitNumber == unit.unitNumber
                        BookUnitCard(
                            unit = unit,
                            isHighlighted = isHighlighted,
                            onStudyTopic = { onNavigateToTopic(unit.mappedTopicId) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(AppSpacing.xl))
                }
            }
        }
    }
}

@Composable
private fun BookUnitCard(
    unit: BookUnitItem,
    isHighlighted: Boolean,
    onStudyTopic: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor by animateColorAsState(
        targetValue = if (isHighlighted) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "unit_highlight_color"
    )

    val borderStroke = if (isHighlighted) {
        BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("book_unit_card_${unit.unitNumber}"),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = borderStroke,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(AppSpacing.xs)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (isHighlighted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "Unit ${unit.unitNumber}",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (isHighlighted) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Surface(
                    shape = MaterialTheme.shapes.extraSmall,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = unit.bookSection,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = unit.unitTitle,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Course Topic Mapping Box
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.6f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = Dimens.spacing12, vertical = AppSpacing.xs)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.xs)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MenuBook,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Column {
                            Text(
                                text = "Mapped Course Topic",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = unit.mappedTopicTitle ?: unit.mappedTopicId,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(AppSpacing.xs))

                    Button(
                        onClick = onStudyTopic,
                        modifier = Modifier.testTag("study_topic_unit_${unit.unitNumber}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Study")
                    }
                }
            }
        }
    }
}
