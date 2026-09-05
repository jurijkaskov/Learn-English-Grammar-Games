package com.learnenglish.grammargames.domain.model

data class Course(
    val id: String,
    val title: String,
    val level: CourseLevel,
    val description: String = ""
)
