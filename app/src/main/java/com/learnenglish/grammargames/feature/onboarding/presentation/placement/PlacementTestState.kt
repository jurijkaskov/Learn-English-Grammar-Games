package com.learnenglish.grammargames.feature.onboarding.presentation.placement

data class PlacementQuestion(
    val id: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int
)

data class PlacementTestUiState(
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isCompleted: Boolean = false,
    val recommendedLevel: String = "Intermediate (B1)",
    val questions: List<PlacementQuestion> = listOf(
        PlacementQuestion(
            id = "q1",
            prompt = "She _____ to the gym every Monday evening.",
            options = listOf("goes", "is going", "go", "has gone"),
            correctIndex = 0
        ),
        PlacementQuestion(
            id = "q2",
            prompt = "By this time next year, they _____ their degree.",
            options = listOf("will finish", "will have finished", "finished", "are finishing"),
            correctIndex = 1
        ),
        PlacementQuestion(
            id = "q3",
            prompt = "If I had known the answer, I _____ you immediately.",
            options = listOf("would tell", "will tell", "would have told", "told"),
            correctIndex = 2
        )
    )
)

sealed interface PlacementTestUiAction {
    data class SelectOption(val optionIndex: Int) : PlacementTestUiAction
    data object NextQuestion : PlacementTestUiAction
}
