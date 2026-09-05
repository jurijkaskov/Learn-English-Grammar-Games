package com.learnenglish.grammargames.feature.mistakes

data class MistakeItem(
    val id: String,
    val topicTitle: String,
    val errorSentence: String,
    val correctedSentence: String,
    val explanation: String,
    val failCount: Int
)

data class MistakesUiState(
    val filterTopic: String? = null,
    val mistakes: List<MistakeItem> = listOf(
        MistakeItem(
            id = "m1",
            topicTitle = "Present Simple vs Continuous",
            errorSentence = "I am wanting a cup of tea.",
            correctedSentence = "I want a cup of tea.",
            explanation = "'Want' expresses a state or desire, not a dynamic action, so it is rarely used in continuous aspect.",
            failCount = 3
        ),
        MistakeItem(
            id = "m2",
            topicTitle = "Past Simple with Specific Time",
            errorSentence = "She has arrived ten minutes ago.",
            correctedSentence = "She arrived ten minutes ago.",
            explanation = "With specific past time expressions like 'ago', 'yesterday', or 'in 2010', always use Past Simple.",
            failCount = 2
        ),
        MistakeItem(
            id = "m3",
            topicTitle = "Subject-Verb Agreement",
            errorSentence = "Every student have submitted homework.",
            correctedSentence = "Every student has submitted homework.",
            explanation = "'Every' is grammatically singular and requires a singular verb.",
            failCount = 1
        )
    )
)

sealed interface MistakesUiAction {
    data class PracticeMistake(val mistakeId: String) : MistakesUiAction
}
