package com.learnenglish.grammargames.core.designsystem.component.panel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import com.learnenglish.grammargames.core.designsystem.theme.AppElevation
import com.learnenglish.grammargames.core.designsystem.theme.AppShapes
import com.learnenglish.grammargames.core.designsystem.theme.AppSpacing

/**
 * Primary gameplay surface container for mini-games, drills, and exercise questions.
 * Provides a distinguished, spacious canvas with soft gaming aesthetics.
 */
@Composable
fun GrammarGamePanel(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    elevation: Dp = AppElevation.level2,
    border: BorderStroke? = BorderStroke(AppElevation.borderHairline, MaterialTheme.colorScheme.outlineVariant),
    contentPadding: PaddingValues = PaddingValues(AppSpacing.cardPaddingLarge),
    testTag: String? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(AppShapes.panelGame)
            .then(if (testTag != null) Modifier.testTag(testTag) else Modifier),
        shape = AppShapes.panelGame,
        color = containerColor,
        border = border,
        shadowElevation = elevation
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            content = content
        )
    }
}
