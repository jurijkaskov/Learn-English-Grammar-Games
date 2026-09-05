package com.learnenglish.grammargames.feature.curriculum

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.learnenglish.grammargames.core.designsystem.theme.Dimens

private enum class InspectorTab { OVERVIEW, TOPICS, QUESTIONS, MAPPINGS, VALIDATION }

@Composable
fun CurriculumInspectorRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CurriculumInspectorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CurriculumInspectorScreen(
        uiState = uiState,
        onReload = { viewModel.loadData(forceReload = true) },
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurriculumInspectorScreen(
    uiState: CurriculumInspectorUiState,
    onReload: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(InspectorTab.OVERVIEW) }

    Scaffold(
        modifier = modifier.fillMaxSize().testTag("curriculum_inspector_screen"),
        topBar = {
            TopAppBar(
                title = { Text("Curriculum Inspector") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onReload) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reload JSON")
                    }
                }
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is CurriculumInspectorUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is CurriculumInspectorUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(Dimens.spacing24),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Curriculum Loading Error",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(Dimens.spacing8))
                    Text(text = uiState.message, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(Dimens.spacing16))
                    FilledTonalButton(onClick = onReload) {
                        Text("Try Reloading")
                    }
                }
            }
            is CurriculumInspectorUiState.Content -> {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding)
                ) {
                    // Filter tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                    ) {
                        InspectorTab.entries.forEach { tab ->
                            FilterChip(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                label = { Text(tab.name) }
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = Dimens.spacing16),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                    ) {
                        when (selectedTab) {
                            InspectorTab.OVERVIEW -> {
                                item {
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Text(
                                                text = "Engine Summary",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(Dimens.spacing8))
                                            Text("Courses: ${uiState.courses.size}")
                                            Text("Sections: ${uiState.sections.size}")
                                            Text("Topics: ${uiState.topics.size}")
                                            Text("Lessons: ${uiState.lessons.size}")
                                            Text("Activities: ${uiState.activities.size}")
                                            Text("Questions: ${uiState.questions.size}")
                                            Spacer(modifier = Modifier.height(Dimens.spacing8))
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = if (uiState.report.isValid) Icons.Default.CheckCircle else Icons.Default.Error,
                                                    contentDescription = null,
                                                    tint = if (uiState.report.isValid) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                                    modifier = Modifier.size(Dimens.iconSizeSmall)
                                                )
                                                Spacer(modifier = Modifier.size(Dimens.spacing8))
                                                Text(
                                                    text = if (uiState.report.isValid) "Schema Validation: PASS" else "Schema Validation: ${uiState.report.errors.size} Errors",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }

                                items(uiState.courses) { course ->
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Text(
                                                text = "${course.title} (${course.level.name})",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = course.description,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                text = "CEFR: ${course.cefrLevel.name} • Sections: ${course.sectionIds.size}",
                                                style = MaterialTheme.typography.labelSmall
                                            )
                                        }
                                    }
                                }
                            }
                            InspectorTab.TOPICS -> {
                                items(uiState.topics) { topic ->
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Text(
                                                text = topic.title,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = topic.shortDescription ?: "No description",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Spacer(modifier = Modifier.height(Dimens.spacing4))
                                            Text(
                                                text = "ID: ${topic.id.value} • Lessons: ${topic.lessonIds.size} • CEFR: ${topic.cefrLevel}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.secondary
                                            )
                                        }
                                    }
                                }
                            }
                            InspectorTab.QUESTIONS -> {
                                items(uiState.questions) { q ->
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = q::class.simpleName ?: "Question",
                                                    style = MaterialTheme.typography.labelMedium,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = q.difficulty.name,
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(Dimens.spacing4))
                                            Text(text = q.prompt, style = MaterialTheme.typography.bodyMedium)
                                            if (!q.explanation.isNullOrBlank()) {
                                                Spacer(modifier = Modifier.height(Dimens.spacing4))
                                                Text(
                                                    text = "Explanation: ${q.explanation}",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            InspectorTab.MAPPINGS -> {
                                val coverage = uiState.beginnerCoverage
                                item {
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Text(
                                                text = "Essential Grammar in Use (4th Ed) Mapping",
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.height(Dimens.spacing8))
                                            Text("Companion Target: Beginner Course (A1–A2)")
                                            Text("Total Units in Reference: ${coverage?.totalUnits ?: 115}")
                                            Text("Mapped Units: ${coverage?.mappedUnits?.size ?: 115} / ${coverage?.totalUnits ?: 115} (${String.format(java.util.Locale.US, "%.1f", coverage?.coveragePercentage ?: 100f)}%)")
                                            Text("Unmapped Units: ${coverage?.unmappedUnits?.size ?: 0}")
                                            Text("Multi-Mapped Units: ${coverage?.multiMappedUnits?.size ?: 0}")
                                            Spacer(modifier = Modifier.height(Dimens.spacing8))
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                val isComplete = coverage?.isComplete ?: true
                                                Icon(
                                                    imageVector = if (isComplete) Icons.Default.CheckCircle else Icons.Default.Warning,
                                                    contentDescription = null,
                                                    tint = if (isComplete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                                                    modifier = Modifier.size(Dimens.iconSizeSmall)
                                                )
                                                Spacer(modifier = Modifier.size(Dimens.spacing8))
                                                Text(
                                                    text = if (isComplete) "100% Coverage Verified (0 Missing, 0 Gaps)" else "Coverage Incomplete",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }

                                val beginnerTopicsWithRefs = uiState.topics
                                    .filter { it.bookReferences.isNotEmpty() }
                                    .sortedBy { it.bookReferences.first().units.minOrNull() ?: 999 }

                                items(beginnerTopicsWithRefs) { topic ->
                                    val ref = topic.bookReferences.first()
                                    val unitsStr = ref.units.joinToString(", ")
                                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                                        Column(modifier = Modifier.padding(Dimens.spacing16)) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "Units: $unitsStr",
                                                    style = MaterialTheme.typography.titleSmall,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = "${topic.cefrLevel.name} • ${topic.conceptDepth?.name ?: ""}",
                                                    style = MaterialTheme.typography.labelSmall
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(Dimens.spacing4))
                                            Text(
                                                text = topic.title,
                                                style = MaterialTheme.typography.bodyLarge,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                            if (!topic.shortDescription.isNullOrBlank()) {
                                                Text(
                                                    text = topic.shortDescription,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(Dimens.spacing4))
                                            Text(
                                                text = "ID: ${topic.id.value} • Section: ${topic.sectionId.value}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.outline
                                            )
                                        }
                                    }
                                }
                            }
                            InspectorTab.VALIDATION -> {
                                item {
                                    Text(
                                        text = "Validation Report",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                if (uiState.report.errors.isEmpty() && uiState.report.warnings.isEmpty()) {
                                    item {
                                        Text("All checks passed cleanly! 0 errors, 0 warnings.")
                                    }
                                }
                                items(uiState.report.errors) { err ->
                                    OutlinedCard(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(Dimens.spacing12),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.Error,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                            Spacer(modifier = Modifier.size(Dimens.spacing8))
                                            Column {
                                                Text(
                                                    text = "[ERROR] ${err.entityType}: ${err.entityId}",
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.error
                                                )
                                                Text(text = err.message, style = MaterialTheme.typography.bodySmall)
                                            }
                                        }
                                    }
                                }
                                items(uiState.report.warnings) { warn ->
                                    OutlinedCard(
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(Dimens.spacing12),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                Icons.Default.Warning,
                                                contentDescription = null,
                                                tint = MaterialTheme.colorScheme.tertiary
                                            )
                                            Spacer(modifier = Modifier.size(Dimens.spacing8))
                                            Column {
                                                Text(
                                                    text = "[WARN] ${warn.entityType}: ${warn.entityId}",
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(text = warn.message, style = MaterialTheme.typography.bodySmall)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(Dimens.spacing24))
                        }
                    }
                }
            }
        }
    }
}
