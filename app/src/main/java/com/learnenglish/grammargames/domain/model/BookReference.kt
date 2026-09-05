package com.learnenglish.grammargames.domain.model

data class BookReference(
    val topicId: String,
    val bookTitle: String,
    val edition: String,
    val units: List<Int>
)
