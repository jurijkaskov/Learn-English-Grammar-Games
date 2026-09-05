package com.learnenglish.grammargames.core.designsystem.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import com.learnenglish.grammargames.core.designsystem.theme.GrammarTypographyTokens
import com.learnenglish.grammargames.core.designsystem.theme.grammarGamesColors

/**
 * High-legibility pedagogical text component for grammar sentences and examples.
 * Designed to maintain outstanding readability under all screen resolutions and font scaling.
 */
@Composable
fun GrammarExampleText(
    text: String,
    modifier: Modifier = Modifier,
    highlightKeyword: String? = null,
    highlightColor: Color = MaterialTheme.grammarGamesColors.primaryAction,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null
) {
    val annotatedString: AnnotatedString = if (!highlightKeyword.isNullOrBlank() && text.contains(highlightKeyword, ignoreCase = true)) {
        buildAnnotatedString {
            val startIndex = text.indexOf(highlightKeyword, ignoreCase = true)
            val endIndex = startIndex + highlightKeyword.length

            append(text.substring(0, startIndex))
            withStyle(
                SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = highlightColor
                )
            ) {
                append(text.substring(startIndex, endIndex))
            }
            append(text.substring(endIndex))
        }
    } else {
        AnnotatedString(text)
    }

    Text(
        text = annotatedString,
        style = GrammarTypographyTokens.grammarExample,
        color = textColor,
        textAlign = textAlign,
        modifier = modifier
    )
}

/**
 * Prominent formula/structure presentation (e.g. have/has + past participle).
 */
@Composable
fun GrammarFormulaText(
    formula: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.grammarGamesColors.primaryAction,
    textAlign: TextAlign? = null
) {
    Text(
        text = formula,
        style = GrammarTypographyTokens.grammarFormula,
        color = color,
        textAlign = textAlign,
        modifier = modifier
    )
}
