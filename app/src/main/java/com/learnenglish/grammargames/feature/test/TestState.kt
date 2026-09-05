package com.learnenglish.grammargames.feature.test

data class TestQuestion(
    val id: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int
)

data class TestUiState(
    val topicId: String = "present_simple",
    val title: String = "Grammar Topic Test",
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val questions: List<TestQuestion> = listOf(
        TestQuestion(
            id = "tq1",
            prompt = "Water _____ at 100 degrees Celsius.",
            options = listOf("boils", "is boiling", "boil", "boiled"),
            correctIndex = 0
        ),
        TestQuestion(
            id = "tq2",
            prompt = "Look! The baby _____ right now.",
            options = listOf("walks", "is walking", "walk", "has walked"),
            correctIndex = 1
        ),
        TestQuestion(
            id = "tq3",
            prompt = "I _____ what you mean.",
            options = listOf("am understanding", "understand", "was understanding", "have understood"),
            correctIndex = 1
        )
    )
)

sealed interface TestUiAction {
    data class SelectOption(val index: Int) : TestUiAction
    data object NextOrSubmit : TestUiAction
}
